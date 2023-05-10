package com.dhana.storio.ui.activity.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dhana.storio.data.StoryRepository
import com.dhana.storio.data.UserRepository
import com.dhana.storio.data.remote.response.StoriesResponse
import com.dhana.storio.data.remote.response.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val storyRepository: StoryRepository, private val userRepository: UserRepository) : ViewModel() {

    fun getAllStories(
        authHeader: String
    ): LiveData<PagingData<Story>> {
        return storyRepository.getAllStories(authHeader).cachedIn(viewModelScope)
    }

    fun getUserToken(): Flow<String?> {
        return userRepository.getUserToken()
    }

    suspend fun logOut(): Flow<Result<Unit>> {
        return userRepository.logout()
    }
}
