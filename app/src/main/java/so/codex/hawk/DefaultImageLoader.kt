package so.codex.hawk

import android.graphics.*
import androidx.core.content.ContextCompat
import so.codex.utils.getColorById
import java.util.*

//ToDo add docs
class DefaultImageLoader private constructor(private var id: String, private var title: String) {

    companion object {
        private val images: MutableMap<String, Bitmap> = mutableMapOf()

        fun get(name: String, id: String): DefaultImageLoader = DefaultImageLoader(name, id)
    }

    init {
        title = removeUselessChars(title)
    }

    private fun removeUselessChars(str: String): String {
        return str.filter {
            it.isLetterOrDigit() || it == ' '
        }.splitToSequence(" ")
            .filter {
                it.isNotEmpty()
            }.take(2)
            .fold("") { acc, s -> acc + s[0] }
            .toUpperCase(Locale.ROOT)
    }

    fun loadImage(): Bitmap {
        if (!isCached()) cacheImage()

        return images[id] ?: error("Error with getting image") //Different level of abstraction, Maybe need replace
    }

    private fun isCached(): Boolean = images[id] != null


    private fun cacheImage() {
        images[id] = createImage()
    }

    private fun createImage(): Bitmap {
        val defaultImage: Bitmap = Bitmap.createBitmap(
            68, 68,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(defaultImage)
        val fontPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 14f * 2.625f + 0.5f
            typeface = Typeface.create("roboto", Typeface.BOLD)
            color = ContextCompat.getColor(ContextProvider.mContext, R.color.header_text_color)
        }
        val bounds = Rect()
        fontPaint.getTextBounds(title, 0, title.length, bounds)
        fontPaint.textAlign = Paint.Align.LEFT
        canvas.drawColor(getColorById(id))
        val centerX = 68f / 2f - bounds.exactCenterX()
        val centerY = 68f / 2f - bounds.exactCenterY()
        canvas.drawText(title, centerX, centerY, fontPaint)

        return defaultImage
    }

}