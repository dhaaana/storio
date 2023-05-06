package com.dhana.storio.data

import com.dhana.storio.data.local.datastore.UserPreferences
import com.dhana.storio.data.remote.api.ApiService
import com.dhana.storio.data.remote.response.LoginResponse
import com.dhana.storio.data.remote.response.RegisterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) {
    suspend fun registerUser(
        name: String,
        email: String,
        password: String
    ): Flow<Result<RegisterResponse>> {
        return flow {
            val response = apiService.registerUser(name, email, password)
            emit(Result.success(response))
        }.catch { throwable ->
            emit(Result.failure(throwable))
        }
    }

    suspend fun loginUser(email: String, password: String): Flow<Result<LoginResponse>> {
        return flow {
            val response = apiService.loginUser(email, password)
            userPreferences.saveUserData(
                response.loginResult.userId,
                response.loginResult.name,
                response.loginResult.token
            )
            emit(Result.success(response))
        }.catch { throwable ->
            emit(Result.failure(throwable))
        }
    }

    suspend fun logout(): Flow<Result<Unit>> {
        return flow {
            userPreferences.clearUserData()
            emit(Result.success(Unit))
        }.catch { throwable ->
            emit(Result.failure(throwable))
        }
    }

    fun isUserLoggedIn(): Flow<Boolean> {
        return userPreferences.getUserToken().map { token ->
            token != null
        }
    }
}
