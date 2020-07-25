package so.codex.hawkapi

import so.codex.sourceinterfaces.IAuthApi
import so.codex.sourceinterfaces.IProfileApi
import so.codex.sourceinterfaces.IWorkspaceApi

/**
 * Interface that declared all necessary api providers for communication with server
 * @author Shiplayer
 * @author YorkIsMine
 */
interface SourceApi {
    /**
     * Provide interface with method that need for work with Authentication
     * @return Implemented interface that ready for communication
     */
    fun getAuthApi(): IAuthApi

    /**
     * Provide interface with method that need for work with Workspace
     * @return Implemented interface that ready for communication
     */
    fun getWorkspaceApi(): IWorkspaceApi

    /**
     * Provide interface with method that need for work with Profile
     * @return Implemented interface that ready for communication
     */
    fun getProfileApi(): IProfileApi
}