package so.codex.hawk

import android.graphics.*
import android.util.Log
import so.codex.codexbl.entity.Workspace
import so.codex.utils.getColorById

object ImageProvider {
    private val images: MutableMap<String, Bitmap> = mutableMapOf()

    fun load(density: Float, workspace: Workspace): Bitmap {

        if (images[workspace.id] == null) {

            var firstChar = ""

            removeUselessChars(workspace.name).split(" ")
                .forEach {
                    firstChar += "$it "
                }

            Log.d("WHATSS", firstChar)

            val defaultImage: Bitmap = Bitmap.createBitmap(
                32, 32,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(defaultImage)
            val fontPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                textSize = 14f * density + 0.5f
                typeface = Typeface.create("roboto", Typeface.BOLD)
                color = 1
            }
            val bounds = Rect()
            fontPaint.getTextBounds(firstChar, 0, firstChar.length, bounds)
            fontPaint.textAlign = Paint.Align.LEFT
            canvas.drawColor(getColorById(workspace.id))
            val centerX = 32 / 2f - bounds.exactCenterX()
            val centerY = 32.toFloat() / 2f - bounds.exactCenterY()
            canvas.drawText(firstChar, centerX, centerY, fontPaint)

            images[workspace.id] = defaultImage
        }

        return images[workspace.id] ?: error("Error with image")
    }

    private fun removeUselessChars(str: String): String {
        var result = ""
        val words = str.split(" ")
        words.forEach {
            result += it.asSequence()
                .filter {c ->
                    c.isLetter()
                }.joinToString("", "", "") + " "
        }

        return result
    }
}