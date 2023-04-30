package com.dhana.storio.data.remote.response

import com.google.gson.annotations.SerializedName

data class AddStoryResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String
)

data class StoriesResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("listStory") val stories: List<Story>
)

data class StoryDetailsResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("story") val story: Story
)

data class Story(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("photoUrl") val photoUrl: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lon") val lon: Double?
)