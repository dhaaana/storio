package com.dhana.storio.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dhana.storio.data.remote.api.ApiService
import com.dhana.storio.data.remote.response.AddStoryResponse
import com.dhana.storio.data.remote.response.StoriesResponse
import com.dhana.storio.data.remote.response.Story
import com.dhana.storio.data.remote.response.StoryDetailsResponse
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
        lon: Double?,
        authHeader: String
    ): Flow<Result<AddStoryResponse>> {
        return flow {
            val response = apiService.addNewStory(description, photo, lat, lon, authHeader)
            emit(Result.success(response))
        }.catch { throwable ->
            emit(Result.failure(throwable))
        }
    }

    fun getAllStoriesWithoutPaging(
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

    fun getAllStories(
        authHeader: String
    ): LiveData<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, authHeader)
            }
        ).liveData
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
