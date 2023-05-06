package com.dhana.storio.data

import com.dhana.storio.data.remote.api.ApiService
import com.dhana.storio.data.remote.response.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class StoryRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun addNewStory(
        description: String,
        photo: MultipartBody.Part,
        lat: Double?,
        lon: Double?
    ): Flow<Result<AddStoryResponse>> {
        return flow {
            val response = apiService.addNewStory(description, photo, lat, lon)
            emit(Result.success(response))
        }.catch { throwable ->
            emit(Result.failure(throwable))
        }
    }

    suspend fun addNewStoryGuest(
        description: String,
        photo: MultipartBody.Part,
        lat: Double?,
        lon: Double?
    ): Flow<Result<AddStoryResponse>> {
        return flow {
            val response = apiService.addNewStoryGuest(description, photo, lat, lon)
            emit(Result.success(response))
        }.catch { throwable ->
            emit(Result.failure(throwable))
        }
    }

    fun getAllStories(
        page: Int?,
        size: Int?,
        location: Int = 0,
        authHeader: String
    ): Flow<Result<StoriesResponse>> {
        return flow {
            val response = apiService.getAllStories(page, size, location, authHeader)
            emit(Result.success(response))
        }.catch { throwable ->
            emit(Result.failure(throwable))
        }
    }

    fun getStoryDetail(
        id: String,
        authHeader: String
    ): Flow<Result<StoryDetailsResponse>> {
        return flow {
            val response = apiService.getStoryDetail(id, authHeader)
            emit(Result.success(response))
        }.catch { throwable ->
            emit(Result.failure(throwable))
        }
    }
}
