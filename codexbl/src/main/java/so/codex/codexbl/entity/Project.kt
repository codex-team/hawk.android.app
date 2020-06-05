package so.codex.codexbl.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Project representation
 * @property id Project ID
 * @property token Project token
 * @property name Project name
 * @property description Project description
 * @property url Project URI
 * @property image Project image
 * @property events Project events
 */
@Parcelize
data class Project(
        val id: String,
        val token: String,
        val name: String,
        val description: String,
        val url: String,
        val image: String,
        val events: List<Event> = listOf()
) : Parcelable