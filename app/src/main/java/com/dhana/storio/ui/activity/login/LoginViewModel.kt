package com.dhana.storio.ui.activity.login

import androidx.lifecycle.ViewModel
import com.dhana.storio.data.UserRepository
import com.dhana.storio.data.remote.response.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    suspend fun loginUser(email: String, password: String): Flow<Result<LoginResponse>> {
        return userRepository.loginUser(email, password)
    }
}