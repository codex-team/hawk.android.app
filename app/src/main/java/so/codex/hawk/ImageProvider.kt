package so.codex.hawk

import android.graphics.*
import android.util.Log
import so.codex.codexbl.entity.Workspace
import so.codex.utils.getColorById

object ImageProvider {
    private val images: MutableMap<String, Bitmap> = mutableMapOf()

    fun load(width: Int, height: Int, density: Float, viewColor: Int, workspace: Workspace): Bitmap {
        Log.d("MEW", "width: $width \n height: $height \n density: $density viewColor: $viewColor \n workspace: $workspace" +
                "\n name: ${workspace.name}")

        if (images[workspace.id] == null) {

            var firstChar = ""

//            val firstChar = removeUselessChars(workspace.name).split(" ")
//                .fold("") { acc, s -> acc + s.first() } ->>>>>>>>>>>>>>>>>>>> this will be error
//                .toString()
//                .toUpperCase()

            removeUselessChars(workspace.name).split(" ") // this code is better than yours
                .forEach {
                    firstChar += "$it "
                }

            Log.d("WHATSS", firstChar)

            val defaultImage: Bitmap = Bitmap.createBitmap(
                width, height, // width and height is 0 and will be error
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(defaultImage)
            val fontPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                textSize = 14f * density + 0.5f
                typeface = Typeface.create("roboto", Typeface.BOLD)
                color = viewColor
            }
            val bounds = Rect()
            fontPaint.getTextBounds(firstChar, 0, firstChar.length, bounds)
            fontPaint.textAlign = Paint.Align.LEFT
            canvas.drawColor(getColorById(workspace.id))
            val centerX = width / 2f - bounds.exactCenterX()
            val centerY = height.toFloat() / 2f - bounds.exactCenterY()
            canvas.drawText(firstChar, centerX, centerY, fontPaint)

            images[workspace.id] = defaultImage
        }

        return images[workspace.id] ?: error("Error with image")
    }
}