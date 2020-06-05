package so.codex.codexbl.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Represent Workspace info
 * @property id Workspace id
 * @property name Workspace name
 * @property description Workspace description
 * @property image Workspace logo image
 * @property projects Workspace projects list
 * @author Shiplayer
 */
@Parcelize
data class Workspace(
        val id: String,
        val name: String,
        val description: String,
        val image: String,
        val projects: List<Project> = listOf()
) : Parcelable