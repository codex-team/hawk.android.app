package so.codex.codexbl.main

import android.graphics.Bitmap

class CachedImages {
    val cache = mutableMapOf<String, Bitmap>()

    fun getImage(uuid: String): Bitmap? {
        return if (cache.containsKey(uuid)) {
            cache[uuid]
        } else {
            null
        }
    }

    fun cacheImage(uuid: String, image: Bitmap, force: Boolean = false): Boolean {
        return if (!cache.containsKey(uuid) || force) {
            cache[uuid] = image
            true
        } else {
            false
        }
    }
}