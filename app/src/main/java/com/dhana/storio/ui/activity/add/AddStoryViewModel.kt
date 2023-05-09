package com.dhana.storio.ui.activity.add

import androidx.lifecycle.ViewModel
import com.dhana.storio.data.StoryRepository
import com.dhana.storio.data.remote.response.AddStoryResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.single
import okhttp3.MultipartBody
import javax.inject.Inject

class AddStoryViewModel @Inject constructor(private val storyRepository: StoryRepository) :
    ViewModel() {

    suspend fun addNewStory(
        description: String,
        photo: MultipartBody.Part,
        lat: Double?,
        lon: Double?
    ): Flow<Result<AddStoryResponse>> {
        return storyRepository.addNewStory(description, photo, lat, lon)
    }
}
