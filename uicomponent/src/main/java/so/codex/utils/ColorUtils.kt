package so.codex.utils

import android.graphics.Color

/**
 * Вспомогательные переменные и функции
 */

private val colors = arrayOf(
    "#15c46d",
    "#36a9e0",
    "#ef4b4b",
    "#4ec520",
    "#b142af",
    "#6632b8",
    "#3251b8",
    "#505b74"
).map {
    Color.parseColor(it)
}

fun getColorById(id: String): Int {
    val ch = id.last()
    return when (ch) {
        in '0'..'9' -> {
            colors[(ch - '0') / 2]
        }
        in 'a'..'f' -> {
            colors[(ch - 'a')]
        }
        else -> Color.WHITE
    }
}