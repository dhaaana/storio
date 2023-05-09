package com.dhana.storio.ui.activity.detail

import androidx.lifecycle.ViewModel
import com.dhana.storio.data.StoryRepository
import com.dhana.storio.data.UserRepository
import com.dhana.storio.data.remote.response.StoryDetailsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val storyRepository: StoryRepository, private val userRepository: UserRepository) :
    ViewModel() {

    fun getStoryDetail(
        id: String,
        authHeader: String
    ): Flow<Result<StoryDetailsResponse>> {
        return storyRepository.getStoryDetail(id, authHeader)
    }

    fun getUserToken(): Flow<String?> {
        return userRepository.getUserToken()
    }
}
