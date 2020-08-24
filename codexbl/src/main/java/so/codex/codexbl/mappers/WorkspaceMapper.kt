package so.codex.codexbl.mappers

import so.codex.codexbl.entity.Workspace
import so.codex.sourceinterfaces.entity.FullWorkspaceEntity
import so.codex.sourceinterfaces.entity.WorkspaceEntity
import so.codex.sourceinterfaces.entity.WorkspaceWithProjectsEntity

/**
 * File contain methods for mapping from WorkspaceEntity to Workspace
 */

/**
 * Convert from WorkspaceEntity to Workspace
 */
fun WorkspaceEntity.toWorkspace(): Workspace {
    return when (this) {
        is FullWorkspaceEntity -> {
            Workspace(
                id = id,
                name = name,
                description = description ?: "",
                image = image,
                projects = projects.map { project ->
                    project.toProject()
                }
            )
        }
        is WorkspaceWithProjectsEntity -> {
            Workspace(
                id = id,
                name = name,
                description = description ?: "",
                image = image,
                projects = projects.map { project ->
                    project.toProject()
                }
            )
        }
        else -> {
            Workspace(
                id = id,
                name = name,
                description = description ?: "",
                image = image,
                projects = listOf()
            )
        }
    }
}