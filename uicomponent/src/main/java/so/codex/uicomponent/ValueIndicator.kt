package so.codex.uicomponent

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.Typeface
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import kotlin.math.ceil
import kotlin.properties.Delegates

/**
 * Более кастомная реализация индикатора (не используется)
 */
class ValueIndicator @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.valueIndicatorStyle
) : View(context, attrs, defStyleAttr) {

    companion object {
        /**
         * Основная цветовая палитра. Данные цвета устанавливаются с помощью [IndicatorColors].
         */
        var DEFAULT_INDICATOR_COLORS = IndicatorColors(Color.RED, Color.GRAY, Color.GREEN)
    }

    /**
     * Основное значение, в зависимости от которого будет определяться, будет ли оно больше, меньше
     * или равно данному числу. Число, которое в данный момент установлено изменяется по переменной
     * [count].
     */
    var baseCount: Int by Delegates.observable(0) { _, old, new ->
        updateTextColors()
    }
    //var color: Int = Color.BLACK
    /**
     * Цветовая палитра, которую можно будет поменять
     */
    var currentTextColors = DEFAULT_INDICATOR_COLORS
    /**
     * Цвет, которое меняется в зависимости от того, [count] больше, чем [baseCount],
     * меньше или равно ему.
     */
    var textColor = currentTextColors.colorEqual
    /**
     * Число, которое сравнивается с [baseCount], при изменении данного числа, устанавливается
     * цвет, в зависимости от того больше, меньше или равно основному числу [baseCount]
     */
    var count: Int by Delegates.observable(0) { _, old, new ->
        updateTextColors()
        showCountText = new.toString()
        invalidate()
        if (old.toString().length != new.toString().length) {
            requestLayout()
        }
    }

    private var showCountText: CharSequence = ""

    var heightTriangle = 0.toDp()
    var widthTriangle = 0.toDp()

    val textPaint = TextPaint().apply {
        isAntiAlias = true
        typeface = Typeface.create("roboto-bold", Typeface.NORMAL)
        style = Paint.Style.FILL
    }

    val trianglePaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    val trianglePathUp = Path()

    var state = IndicatorState.EQUAL

    var heightText: Float = 0f

    val trianglePathDown = Path()

    val textRect = Rect()

    val linePaint = Paint().apply {
        this.strokeWidth = 3.toDp()
    }

    val betweenPadding = 2.2f.toDp()

    var startX = 0f

    var baseY = 0f

    init {
        context.obtainStyledAttributes(
                attrs,
                R.styleable.ValueIndicator,
                defStyleAttr,
                R.style.Codex_Widgets_ValueIndicatorStyle
        ).apply {
            val colorMore = getColor(
                    R.styleable.ValueIndicator_colorMore,
                    DEFAULT_INDICATOR_COLORS.colorMore
            )
            val colorEqual = getColor(
                    R.styleable.ValueIndicator_colorMore,
                    DEFAULT_INDICATOR_COLORS.colorEqual
            )
            val colorLess = getColor(
                    R.styleable.ValueIndicator_colorMore,
                    DEFAULT_INDICATOR_COLORS.colorLess
            )
            currentTextColors = IndicatorColors(colorMore, colorEqual, colorLess)

            baseCount = getInt(R.styleable.ValueIndicator_baseCount, 0)

            count = 0

            val fontSize = getDimension(R.styleable.ValueIndicator_fontSize, 12.toDp())
            textPaint.textSize = fontSize
            recycle()
        }
    }

    private fun updateTextColors() {
        textColor = when {
            count > baseCount -> {
                state = IndicatorState.MORE
                currentTextColors.colorMore
            }
            count == baseCount -> {
                state = IndicatorState.EQUAL
                currentTextColors.colorEqual
            }
            else -> {
                state = IndicatorState.LESS
                currentTextColors.colorLess
            }
        }
        trianglePaint.color = textColor
        textPaint.color = textColor
        linePaint.color = textColor
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        textPaint.getTextBounds(count.toString(), 0, count.toString().length, textRect)
        heightTriangle = textRect.height().toFloat()
        widthTriangle = heightTriangle

        heightText = textRect.height().toFloat()

        val suggestWidth =
                ceil(widthTriangle + betweenPadding + textPaint.measureText(count.toString()) + paddingStart + paddingEnd).toInt()
        val suggestHeight = ceil(heightTriangle + paddingTop + paddingBottom).toInt()

        widthSize = when (widthMode) {
            MeasureSpec.EXACTLY -> {
                Math.max(widthSize, suggestWidth)
            }
            MeasureSpec.AT_MOST -> {
                Math.min(widthSize, suggestWidth)
            }
            else -> {
                suggestWidth
            }
        }
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

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val widthView = minimumWidth()
        startX = paddingLeft.toFloat()
        baseY = heightTriangle + paddingTop

        trianglePathUp.reset()
        trianglePathUp.moveTo(startX + widthTriangle / 2, baseY - heightTriangle)
        trianglePathUp.lineTo(startX, baseY)
        trianglePathUp.lineTo(startX + widthTriangle, baseY)
        trianglePathUp.lineTo(startX + widthTriangle / 2, baseY - heightTriangle)

        trianglePathDown.reset()
        trianglePathDown.moveTo(startX, baseY - heightTriangle)
        trianglePathDown.lineTo(startX + widthTriangle, baseY - heightTriangle)
        trianglePathDown.lineTo(startX + widthTriangle / 2, baseY)
        trianglePathDown.moveTo(startX, baseY - heightTriangle)
    }

    fun minimumWidth(): Float {
        return widthTriangle + betweenPadding + textPaint.measureText(count.toString())
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        when (state) {
            IndicatorState.MORE -> {
                canvas.drawPath(trianglePathDown, trianglePaint)
            }
            IndicatorState.LESS -> {
                canvas.drawPath(trianglePathUp, trianglePaint)
            }
            IndicatorState.EQUAL -> {
                canvas.drawLine(
                        startX,
                        baseY - 7.5f.toDp(),
                        startX + widthTriangle,
                        baseY - 7.5f.toDp(),
                        linePaint
                )
                canvas.drawLine(
                        startX,
                        baseY - 2f.toDp(),
                        startX + widthTriangle,
                        baseY - 2f.toDp(),
                        linePaint
                )
            }
        }
        canvas.drawText(
                showCountText.toString(),
                startX + widthTriangle + betweenPadding,
                baseY,
                textPaint
        )

        /*canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = 0.5f.toDp()
            color = Color.BLACK
        })*/
        canvas.restore()
    }

    private fun Int.toDp(): Float = this * resources.displayMetrics.density
    private fun Float.toDp(): Float = this * resources.displayMetrics.density

    data class IndicatorColors(val colorMore: Int, val colorEqual: Int, val colorLess: Int)
    enum class IndicatorState {
        MORE, EQUAL, LESS
    }
}