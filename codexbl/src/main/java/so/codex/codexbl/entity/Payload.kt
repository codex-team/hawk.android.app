package so.codex.codexbl.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date

/**
 * Type representing Event payload
 * @property title Event title
 * @property timestamp Event timestamp
 */
@Parcelize
data class Payload(
    val title: String,
    val timestamp: Date
) : Parcelable