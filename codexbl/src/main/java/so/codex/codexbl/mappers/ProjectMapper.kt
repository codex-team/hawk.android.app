package so.codex.codexbl.mappers

import so.codex.codexbl.entity.Project
import so.codex.sourceinterfaces.entity.ProjectEntity

/**
 * File contain methods for mapping from ProjectEntity to Project
 */

/**
 * Convert ProjectEntity to Project
 * @return Return Project
 */
internal fun ProjectEntity.toProject(): Project {
    return Project(
        id = id,
        token = token,
        name = name,
        description = description,
        url = url,
        image = image,
        events = listOf()
    )
}
