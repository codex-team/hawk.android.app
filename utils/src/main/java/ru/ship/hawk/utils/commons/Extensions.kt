package ru.ship.hawk.utils.commons

import android.util.Log

/**
 * Extension for logged info message, used tag of class in where we try to call this extension
 * @param message The text that should be displayed in the console
 */
fun Any.info(message: String) {
    Log.i(this::class.java.simpleName, message)
}

/**
 * Extension for logged warning message, used tag of class in where we try to call this extension
 * @param message The text that should be displayed in the console
 * @param error The [Throwable] that we want to display in the console
 */
fun Any.warning(message: String, error: Throwable? = null) {
    if (error != null) {
        Log.w(this::class.java.simpleName, message, error)
    } else {
        Log.w(this::class.java.simpleName, message)
    }
}

/**
 * Extension for logged error message, used tag of class in where we try to call this extension
 * @param message The text that should be displayed in the console
 * @param error The [Throwable] that we want to display in the console
 */
fun Any.error(message: String, error: Throwable? = null) {
    if (error != null) {
        Log.e(this::class.java.simpleName, message, error)
    } else {
        Log.e(this::class.java.simpleName, message)
    }
}
