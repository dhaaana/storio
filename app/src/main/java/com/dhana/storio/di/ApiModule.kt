package com.dhana.storio.di

import com.dhana.storio.data.remote.api.ApiConfig
import com.dhana.storio.data.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return ApiConfig.getApiService()
    }
}