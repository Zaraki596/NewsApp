package com.example.newsapp.data.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.example.newsapp.utils.State
import kotlinx.coroutines.flow.*
import retrofit2.Response

/**
 * A repository which provides resource from local database as well as remote end point.
 *
 * [RESULT] represents the type for database.
 * [REQUEST] represents the type for network.
 */
abstract class NetworkBoundRepository<RESULT, REQUEST> {

    fun asFlow() = flow<State<RESULT>> {

        // Emit Loading State
        emit(State.loading())

        try {
            // Emit Database content first
            emit(State.success(fetchFromLocal().first()))

            // Fetch latest articles from remote
            val apiResponse = fetchFromRemote()

            // Parse body
            val remoteArticles = apiResponse.body()

            // Check for response validation
            if (apiResponse.isSuccessful && remoteArticles != null) {
                // Save articles into the persistence storage
                saveRemoteData(remoteArticles)
            } else {
                // Something went wrong! Emit Error state.
                emit(State.error(apiResponse.message()))
            }
        } catch (e: Exception) {
            // Exception occurred! Emit error
            emit(State.error("Network error! Can't get latest Articles."))
            e.printStackTrace()
        }

        // Retrieve posts from persistence storage and emit
        emitAll(fetchFromLocal().map {
            State.success<RESULT>(it)
        })
    }

    /**
     * Saves retrieved from remote into the persistence storage.
     */
    @WorkerThread
    protected abstract suspend fun saveRemoteData(response: REQUEST)

    /**
     * Retrieves all data from persistence storage.
     */
    @MainThread
    protected abstract fun fetchFromLocal(): Flow<RESULT>

    /**
     * Fetches [Response] from the remote end point.
     */
    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<REQUEST>
}