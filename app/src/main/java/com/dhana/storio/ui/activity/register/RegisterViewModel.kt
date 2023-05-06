package com.dhana.storio.ui.activity.register

import androidx.lifecycle.ViewModel
import com.dhana.storio.data.UserRepository
import com.dhana.storio.data.remote.response.RegisterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    suspend fun registerUser(
        name: String,
        email: String,
        password: String
    ): Flow<Result<RegisterResponse>> {
        return userRepository.registerUser(
            name,
            email,
            password,
        )
    }
}