package so.codex.uicomponent

import android.view.View
import android.widget.TextView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Делегаты для взаимодействия с [TextView], получение строки в установки текста через [String]
 */
fun textViewDelegate(textView: TextView): ReadWriteProperty<Any, String> {
    return object : ReadWriteProperty<Any, String> {
        override fun getValue(thisRef: Any, property: KProperty<*>): String {
            return textView.text.toString()
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
            textView.text = value
        }

    }
}

/**
 * Делегаты для взаимодействия с [TextView], получение строки в установки текста через [String]
 * с выполнением условия [condition]
 * @param condition Условие которое будет вызываться каждый раз при установки значения
 */
fun textViewDelegate(textView: TextView, condition: (View, String) -> Unit): ReadWriteProperty<Any, String> {
    return object : ReadWriteProperty<Any, String> {
        override fun getValue(thisRef: Any, property: KProperty<*>): String {
            return textView.text.toString()
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
            condition(textView, value)
            textView.text = value
        }

    }
}