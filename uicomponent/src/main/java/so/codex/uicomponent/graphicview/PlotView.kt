package so.codex.uicomponent.graphicview

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.Rect
import android.graphics.Shader
import android.graphics.Typeface
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import so.codex.uicomponent.R
import kotlin.math.ceil
import kotlin.math.roundToInt
import kotlin.properties.Delegates

/**
 * PlotView используется для отображения графика по указанным точкам и показывает текст для
 * соответствующих точек плд графиком.
 */
class PlotView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.plotViewStyle
) : View(context, attrs, defStyleAttr) {

    //private val foregroundDrawable: Drawable = ContextCompat.getDrawable(context, R.drawable.background)!!
    /**
     * maxX ялвяется максимальное значение, которое встречается в [data] по координате X
     */
    private var maxX = 0
    /**
     * maxY ялвяется максимальное значение, которое встречается в [data] по координате Y
     */
    private var maxY = 0

    /**
     * Отступы графика слева
     */
    private var plotPaddingLeft: Float = 0f

    /**
     * Отступы графика справа
     */
    private var plotPaddingRight: Float = 0f

    /**
     * Отступы графика снизу
     * По умолчанию: 12dp
     */
    private var plotPaddingBottom: Float = 39.toDp()

    /**
     * Отступы графика сверху
     */
    private var plotPaddingTop: Float = 0f

    /**
     * Начальный цвет градиента
     * По умолчанию: цвет черный
     */
    private var startColor = Color.BLACK

    /**
     * Конечный цвет градиента
     * По умолчанию: цвет черный
     */
    private var endColor = Color.BLACK

    /**
     * Количество точек, которое отображается на графике
     * По умолчанию: 10
     */
    private var plotCount = 10

    /**
     * Шрифт используется для отображения в заданном шрифте подписи под графиком
     */
    private var typefaceLabel: Typeface? = null

    /**
     * Размер текста под графиком
     * По умолчанию: 10dp
     */
    private var labelFontSize = 10.toDp()
    /**
     * Цвет текста под графиком
     * По умолчанию: цвет серый
     */
    private var labelFontColor = Color.GRAY

    private var isTouched = false

    private var selectedPoint: Point? = null

    private var animation: ObjectAnimator = ObjectAnimator.ofFloat(this, "phase", 1f, 0f).apply {
        duration = 2000
    }

    init {
        context.obtainStyledAttributes(
                attrs,
                R.styleable.PlotView,
                defStyleAttr,
                0
        ).apply {
            plotPaddingLeft = getDimension(R.styleable.PlotView_plotPaddingLeft, plotPaddingLeft)
            plotPaddingRight = getDimension(R.styleable.PlotView_plotPaddingRight, plotPaddingRight)
            plotPaddingBottom = getDimension(
                    R.styleable.PlotView_plotPaddingBottom,
                    plotPaddingBottom
            )
            plotPaddingTop = getDimension(R.styleable.PlotView_plotPaddingTop, plotPaddingTop)
            startColor = getColor(R.styleable.PlotView_startColor, startColor)
            endColor = getColor(R.styleable.PlotView_endColor, endColor)
            plotCount = getInteger(R.styleable.PlotView_plotCount, plotCount)
            val style = getInteger(R.styleable.PlotView_labelFontStyle, Typeface.NORMAL)
            typefaceLabel = Typeface.create(getString(R.styleable.PlotView_labelFontFamily).let {
                if (it.isNullOrEmpty()) {
                    "self"
                } else {
                    it
                }
            }, style)
            labelFontSize = getDimension(R.styleable.PlotView_labelFontSize, labelFontSize)
            labelFontColor = getColor(R.styleable.PlotView_labelFontColor, labelFontColor)
            recycle()
        }
    }

    /**
     * Определяем [Paint] для рисования линий графика
     */
    private var paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 2.toDp()
    }

    /**
     * Опеределяет путь, по которому должны идти линии
     */
    private val path = Path()

    /**
     * Ширина графика с уже включенными в него отступами
     */
    private val mWidth
        get() = width - (paddingLeft + plotPaddingLeft + paddingRight + plotPaddingRight)

    /**
     * Высота графика с уже включенными в него отступами
     */
    private val mHeight
        get() = height - (paddingBottom + plotPaddingBottom + paddingTop + plotPaddingTop)

    /**
     * [PathMeasure] используется для определения длины линий, чтоб использовать ее для анимации
     */
    private var measure: PathMeasure? = null

    /**
     * Поле, которое принимает список точек, которые необходимо получить. После получения данного
     * списка вызывается функция [handleData] для обработки их и вычисления точек на графике
     */
    public var data by Delegates.observable(listOf<Point>()) { _, old, new ->
        handleData(new)
        if (isAttachedToWindow) {
            animation.start()
        }
    }

    /**
     * Берет максимальное значение по X и Y, устанавливает градиет для кисти, берет последние
     * [plotCount] элементов для отображения, вычисляет для них точки и запускает анимацию
     * @param new Список точек, которые необходимо отобразить
     */
    private fun handleData(new: List<Point>) {
        post {
            maxX = new.maxBy { it.x }?.x ?: 0
            maxY = new.maxBy { it.y }?.y ?: 0
            path.reset()
            val width = mWidth
            val height = mHeight
            paint.shader = LinearGradient(
                    0f,
                    height,
                    0f,
                    0f,
                    startColor,
                    endColor,
                    Shader.TileMode.REPEAT
            )
            val points = new.sortedBy { it.x }.takeLast(plotCount)
            val step = width / (points.size - 1)
            Log.i("PlotView", "step point = $step")
            points.forEachIndexed { i, it ->
                val y = height * (1 - it.y.toFloat() / maxY)
                val x = plotPaddingLeft + (step * i)
                Log.i("PlotViewPoint", "($x, $y) ($width, $height)")
                if (i == 0) {
                    path.moveTo(x, y)
                }
                path.lineTo(x, y)
            }
            measure = PathMeasure(path, false)
        }
    }

    /**
     * Список [Label], в которых определены необходимые тексты для отображения с позицией
     */
    private var mXLabel: List<Label> = listOf()

    /**
     * Определяет кисть для рисования текста с указанным ранее [typefaceLabel], [labelFontColor] и
     * [labelFontSize]
     */
    private val labelPaint = TextPaint().apply {
        isAntiAlias = true
        textSize = labelFontSize
        typeface = typefaceLabel
        color = labelFontColor
        isLinearText = true
        textAlign = Paint.Align.LEFT
    }

    /**
     * При установлении нового списка для отображения подписи под графиком вызывает метод [handleLabels]
     */
    public var xLabel by Delegates.observable(listOf<String>()) { _, old, new ->
        handleLabels(new)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val suggestHeight = ceil(56.toDp() + plotPaddingBottom + paddingTop + paddingBottom + plotPaddingTop).toInt()

        heightSize = when (heightMode) {
            MeasureSpec.EXACTLY -> {
                Math.max(heightSize, suggestHeight)
            }
            MeasureSpec.AT_MOST -> {
                Math.min(heightSize, suggestHeight)
            }
            else -> {
                suggestHeight
            }
        }
        setMeasuredDimension(widthSize, heightSize)
    }

    /**
     * В данном методе берется количество точек из [data], кроме первой и последней и берется
     * значение из [new], если текст влазит в доступное место, то отображается нормально, иначе
     * часть текста скрывается и вместо нее отображается многоточие. Вычисляет позицию, на которой
     * текст должен располагаться.
     * @param new Список текстов, которые необходимо отобразить под графиком к соответствующим точкам
     */
    private fun handleLabels(new: List<String>) {
        post {
            val lastLabels = new.takeLast(plotCount - 1).take(plotCount - 2)
            val width = mWidth
            val offset = labelPaint.descent() - labelPaint.ascent()
            val height = mHeight + paddingTop + plotPaddingTop + offset
            val step = width / (lastLabels.size + 1)
            Log.i("PlotView", "step = $step")
            val betweenPadding = 2.toDp()
            mXLabel = mutableListOf<Label>().apply {
                lastLabels.forEachIndexed { ind, it ->
                    addAll(it.toLabelList(step, betweenPadding, height, ind + 1))
                }
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        post {
            animation.start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animation.cancel()
    }

    override fun invalidate() {
        super.invalidate()
        if (!animation.isStarted || !animation.isRunning || !isTouched) {
            invalidateData()
        }
        if (isTouched) {
            isTouched = false
        }
    }

    private fun invalidateData() {
        handleData(data)
        handleLabels(xLabel)
    }

    /**
     * Данная функция вызывается во время выполнения анимации, для отображения графика
     */
    fun setPhase(phase: Float) {
        Log.i("PlotView", "Phase is $phase")
        paint.pathEffect = DashPathEffect(
                floatArrayOf(
                        measure?.length ?: 0f,
                        measure?.length ?: 0f
                ), phase * (measure?.length ?: 0f)
        ).also {
            Log.i("PlotView", "${measure?.length ?: 0f}, ${phase * (measure?.length ?: 0f)}")
        }
        invalidate()
    }

    override fun getMinimumWidth(): Int {
        return (resources.displayMetrics.density * 320).roundToInt()
    }

    override fun getMinimumHeight(): Int {
        return (resources.displayMetrics.density * 100).roundToInt()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawPath(path, paint)
        canvas.drawLine(
                0f,
                mHeight + paddingTop + plotPaddingTop,
                width + paddingStart + plotPaddingLeft,
                mHeight + paddingTop + plotPaddingTop,
                paint
        )
        mXLabel.forEach {
            canvas.drawText(it.text.toString(), it.x, it.y, labelPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            isTouched = true

        }
        return super.onTouchEvent(event)
    }

    fun Int.toDp(): Float = resources.displayMetrics.density * this

    data class Point(val x: Int, val y: Int)

    /**
     * Использутеся внутри класса [PlotView] для отображения надписей под графиком
     * @param text Текст, который будет отображаться под графиком соответствующий точке
     * @param x Х координата в плоскости
     * @param y Y координата в плоскости
     */
    private data class Label(val text: CharSequence, val x: Float, val y: Float)

    private fun String.toLabelList(step: Float, betweenPadding: Float, y: Float, offset: Int): List<Label> {
        val lineOffset = labelPaint.descent() - labelPaint.ascent()
        return this.split(" ").mapIndexed { index, text ->
            val ellipsizedText = TextUtils.ellipsize(
                    text,
                    labelPaint,
                    step - betweenPadding * 2,
                    TextUtils.TruncateAt.END
            )
            val measureText = labelPaint.measureText(ellipsizedText.toString())
            val textRect = Rect()
            labelPaint.getTextBounds(text, 0, text.length, textRect)
            val offsetText = (step - betweenPadding * 2) / 2
            Label(
                    ellipsizedText.toString(),
                    step * offset - textRect.exactCenterX(),
                    y + lineOffset * index
            ).also { l ->
                Log.i(
                        "PlotView",
                        "Label$offset text = ${l.text} x = ${l.x} y = ${l.y} step = ${step * offset} offsetText = $offsetText measureText = $measureText"
                )
            }
        }
    }


    private data class PlotPoint(val x: Float, val y: Float, val point: Point)
}