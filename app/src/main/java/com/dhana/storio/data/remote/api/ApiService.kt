package com.dhana.storio.data.remote.api

import com.dhana.storio.data.remote.response.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {
    @POST("register")
    @FormUrlEncoded
    suspend fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @POST("login")
    @FormUrlEncoded
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @Multipart
    @POST("stories")
    suspend fun addNewStory(
        @Part("description") description: String,
        @Part photo: MultipartBody.Part,
        @Part("lat") lat: Double?,
        @Part("lon") lon: Double?,
        @Header("Authorization") authHeader: String,
    ): AddStoryResponse

    @POST("stories/guest")
    @Multipart
    suspend fun addNewStoryGuest(
        @Part("description") description: String,
        @Part photo: MultipartBody.Part,
        @Part("lat") lat: Double?,
        @Part("lon") lon: Double?
    ): AddStoryResponse

    @GET("stories")
    suspend fun getAllStories(
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("location") location: Int = 0,
        @Header("Authorization") authHeader: String
    ): StoriesResponse

    @GET("stories/{id}")
    suspend fun getStoryDetail(
        @Path("id") id: String,
        @Header("Authorization") authHeader: String
    ): StoryDetailsResponse
}