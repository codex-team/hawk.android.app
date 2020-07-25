package so.codex.hawk

import android.util.Log

fun Any.info(message: String) {
    Log.i(this::class.java.simpleName, message)
}

fun Any.warning(message: String) {
    Log.w(this::class.java.simpleName, message)
}

fun Any.e(message: String, throwable: Throwable? = null) {
    if (throwable != null)
        Log.e(this::class.java.simpleName, message, throwable)
    else
        Log.e(this::class.java.simpleName, message)
}