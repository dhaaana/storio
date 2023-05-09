package com.dhana.storio.ui.activity.maps

import androidx.lifecycle.ViewModel
import com.dhana.storio.data.StoryRepository
import com.dhana.storio.data.UserRepository
import com.dhana.storio.data.remote.response.StoriesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(private val storyRepository: StoryRepository, private val userRepository: UserRepository) : ViewModel() {

    fun getAllStories(
        page: Int?,
        size: Int?,
        location: Int = 0,
        authHeader: String
    ): Flow<Result<StoriesResponse>> {
        return storyRepository.getAllStories(page, size, location, authHeader)
    }

    fun getUserToken(): Flow<String?> {
        return userRepository.getUserToken()
    }
}
