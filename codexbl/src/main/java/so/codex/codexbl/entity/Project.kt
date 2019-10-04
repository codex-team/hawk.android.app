package so.codex.codexbl.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Project(
    override val id: String,
    override val token: String,
    override val name: String,
    override val description: String,
    override val url: String,
    override val image: String,
    val events: List<Event>
) : ProjectCommon(
    id,
    token,
    name,
    description,
    url,
    image
)

@Parcelize
open class ProjectCommon(
    open val id: String,
    open val token: String,
    open val name: String,
    open val description: String,
    open val url: String,
    open val image: String
) : Parcelable