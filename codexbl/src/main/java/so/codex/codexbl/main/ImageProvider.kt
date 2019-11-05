package so.codex.codexbl.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import java.lang.ref.WeakReference

class ImageProvider {
    var cache = WeakReference<CachedImages>(CachedImages())

    companion object {
        val instance = ImageProvider()
    }

    fun getImageByUuid(uuid: String, createDefaultImage: () -> Bitmap): Bitmap {
        return getImageByUuid(uuid, createDefaultImage, false)
    }

    private fun getImageByUuid(
        uuid: String,
        createDefaultImage: () -> Bitmap,
        isRepeat: Boolean
    ): Bitmap {
        return if (!cache.isEnqueued || cache.get() == null) {
            cache = WeakReference(CachedImages())
            if (!isRepeat)
                getImageByUuid(uuid, createDefaultImage, true)
            else {
                createDefaultImage()
            }
        } else {
            cache.get()!!.let {
                val image = it.getImage(uuid)
                return image
                    ?: createDefaultImage().also { defaultImage ->
                        it.cacheImage(uuid, defaultImage)
                    }
            }
        }
    }

    fun getImageByUuid(
        uuid: String,
        width: Int,
        height: Int,
        textSize: Float = 0f,
        textColor: Int,
        backgroundColor: Int,
        text: String = ""
    ): Bitmap {
        return getImageByUuid(uuid) {
            val defaultImage = Bitmap.createBitmap(
                    width,
                    height,
                    Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(defaultImage!!)
            val fontPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                this.textSize = textSize
                typeface = Typeface.create("roboto", Typeface.BOLD)
                color = textColor
            }
            val bounds = Rect()
            fontPaint.getTextBounds(text, 0, text.length, bounds)
            fontPaint.textAlign = Paint.Align.LEFT
            canvas.drawColor(backgroundColor)
            val centerX = width / 2f - bounds.exactCenterX()
            val centerY = height.toFloat() / 2f - bounds.exactCenterY()
            canvas.drawText(text, centerX, centerY, fontPaint)
            defaultImage
        }
    }

    /*fun getImageByUuid(
        context: Context,
        uuid: String,
        text: String,
        backgroundColor: Int
    ): Bitmap {
        getImageByUuid(
                uuid,
                20.toDp(context),
                20.toDp(context),
                14.toDp(context),
                context.getColor()
        )
    }*/

    private fun Int.toDp(context: Context) = context.resources.displayMetrics.density * this
}