package so.codex.hawk

import android.graphics.*
import android.util.Log
import androidx.core.content.ContextCompat
import so.codex.utils.getColorById
import java.util.*

/**
 * Class for providing default images
 * when image from network does not exist
 *
 * @param id needed for checking image on id
 * @param title for generating default image
 * @author YorkIsMine
 */
class DefaultImageLoader private constructor(private var id: String, private var title: String) {

    companion object {
        /**
         * Map for cached images
         */
        private val images: MutableMap<String, Bitmap> = mutableMapOf()

        /**
         * Instance of DefaultImageLoader
         */
        fun get(id: String, name: String): DefaultImageLoader = DefaultImageLoader(id, name)
    }

    init {
        title = removeUselessChars(title)
    }

    /**
     * Remove useless chars (e.g. ^%*$# and etc.)
     * @param str for removing useless chars
     * @return new needed string
     */
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

    /**
     * Load image
     * if the image does not exists
     * create new instance and return it
     * @return image
     */
    fun loadImage(): Bitmap {
        if (!isCached()) cacheImage()

        return images[id] ?: error("Error with getting image")
    }

    /**
     * checks if the image is cached
     */
    private fun isCached(): Boolean = images[id] != null


    /**
     * Cached new image in [images]
     */
    private fun cacheImage() {
        images[id] = createImage()
    }

    /**
     * Create default image
     * @return created image
     */
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