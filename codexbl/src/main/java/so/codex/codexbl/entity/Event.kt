package so.codex.codexbl.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Represents information about events
 * Data class that used on domain and ui layer.
 * @property id Unique identification of event
 * @property catcherType Catcher type
 * @property payload Event payload
 */
@Parcelize
data class Event(
        val id: String,
        val catcherType: String,
        val payload: Payload
) : Parcelable