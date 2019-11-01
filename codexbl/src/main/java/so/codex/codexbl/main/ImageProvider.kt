package so.codex.codexbl.main

import android.graphics.Bitmap
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
        if (!cache.isEnqueued || cache.get() == null) {
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
}