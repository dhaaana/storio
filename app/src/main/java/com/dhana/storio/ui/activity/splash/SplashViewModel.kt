package com.dhana.storio.ui.activity.splash

import androidx.lifecycle.ViewModel
import com.dhana.storio.data.UserRepository
import com.dhana.storio.data.remote.response.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    fun isUserLoggedIn(): Flow<Boolean> {
        return userRepository.isUserLoggedIn()
    }
}