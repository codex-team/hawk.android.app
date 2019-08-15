package so.codex.uicomponent

import android.widget.TextView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

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