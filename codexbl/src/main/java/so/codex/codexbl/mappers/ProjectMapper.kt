package so.codex.codexbl.mappers

import so.codex.codexbl.entity.Project
import so.codex.sourceinterfaces.entity.ProjectEntity

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
