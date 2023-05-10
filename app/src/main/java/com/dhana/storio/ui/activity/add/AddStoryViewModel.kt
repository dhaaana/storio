package com.dhana.storio.ui.activity.add

import androidx.lifecycle.ViewModel
import com.dhana.storio.data.StoryRepository
import com.dhana.storio.data.UserRepository
import com.dhana.storio.data.remote.response.AddStoryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class AddStoryViewModel @Inject constructor(private val storyRepository: StoryRepository, private val userRepository: UserRepository) :
    ViewModel() {

    suspend fun addNewStory(
        description: String,
        photo: MultipartBody.Part,
        lat: Double?,
        lon: Double?,
        authHeader: String
    ): Flow<Result<AddStoryResponse>> {
        return storyRepository.addNewStory(description, photo, lat, lon, authHeader)
    }

    fun getUserToken(): Flow<String?> {
        return userRepository.getUserToken()
    }
}
