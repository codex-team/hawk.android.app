package so.codex.codexbl.koin

import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import so.codex.codexbl.base.CacheRepository
import so.codex.codexbl.base.SourceRepository
import so.codex.codexbl.base.StorageRepository
import so.codex.codexbl.entity.Workspace
import so.codex.codexbl.interactors.IProfileInteractor
import so.codex.codexbl.interactors.ISignInInteractor
import so.codex.codexbl.interactors.ISignUpInteractor
import so.codex.codexbl.interactors.IUserInteractor
import so.codex.codexbl.interactors.IWorkspaceInteractor
import so.codex.codexbl.interactors.ProfileInteractor
import so.codex.codexbl.interactors.SignInInteractor
import so.codex.codexbl.interactors.SignUpInteractor
import so.codex.codexbl.interactors.UserInteractor
import so.codex.codexbl.interactors.WorkspaceInteractor
import so.codex.codexbl.interactors.projects.IProjectInteractor
import so.codex.codexbl.interactors.projects.ProjectInteractor
import so.codex.codexbl.providers.UserTokenProviderImpl
import so.codex.codexbl.providers.projects.ProjectProvider
import so.codex.codexbl.providers.workspaces.WorkspaceProvider
import so.codex.codexbl.repository.WorkspaceRepository
import so.codex.codexbl.repository.dao.WorkspaceResponseDao
import so.codex.core.UserTokenProvider


/**
 * Module that need on authorization scope
 */
val authInteractorsModule = module {
    factory<ISignInInteractor> { SignInInteractor(get()) }
    factory<ISignUpInteractor> { SignUpInteractor(get()) }
    factory<IUserInteractor> { UserInteractor() }
}

/**
 * All interactors for communication
 */
val interactorsModule = module {
    factory<IWorkspaceInteractor> { WorkspaceInteractor() }
    factory<IProfileInteractor> { ProfileInteractor(get()) }
    factory<IProjectInteractor> { ProjectInteractor(get()) }
}

/**
 * Module where attached all dependencies that need on main activity scope
 */
val mainActivityInteractorsModule = module {

}

/**
 * Provide storage or other providers
 * Предоставляют какое-то хранилище для какой-то информации
 */
val providersModule = module {
    single<UserTokenProvider> { UserTokenProviderImpl(get(), get()) }
    single {
        WorkspaceProvider(
            get(qualifier("workspace_cache")),
            get(qualifier("workspace_api"))
        )
    }
    single {
        ProjectProvider(
            get()
        )
    }
}

val repositoriesModule = module {
    single<SourceRepository<WorkspaceResponseDao>>(qualifier("workspace_api")) {
        WorkspaceRepository(get(), get())
    }
    single<StorageRepository<List<Workspace>>>(qualifier("workspace_cache")) {
        CacheRepository(
            emptyList()
        )
    }
}