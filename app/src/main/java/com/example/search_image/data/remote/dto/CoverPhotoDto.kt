package com.example.search_image.data.remote.dto


import com.google.gson.annotations.SerializedName

data class CoverPhotoDto(
    @SerializedName("alt_description")
    val altDescription: String?,
    @SerializedName("blur_hash")
    val blurHash: String?,
    @SerializedName("color")
    val color: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("current_user_collections")
    val currentUserCollections: List<Any?>?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean?,
    @SerializedName("likes")
    val likes: Int?,
    @SerializedName("links")
    val links: LinksDto?,
    @SerializedName("premium")
    val premium: Boolean?,
    @SerializedName("promoted_at")
    val promotedAt: String?,
    @SerializedName("sponsorship")
    val sponsorship: Any?,
    @SerializedName("topic_submissions")
    val topicSubmissions: TopicSubmissionsDto?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("urls")
    val urls: UrlsDto?,
    @SerializedName("user")
    val user: UserDto?,
    @SerializedName("width")
    val width: Int?
)