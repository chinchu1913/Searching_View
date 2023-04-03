package com.example.search_image.data.remote.dto


import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("accepted_tos")
    val acceptedTos: Boolean?,
    @SerializedName("bio")
    val bio: Any?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("for_hire")
    val forHire: Boolean?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("instagram_username")
    val instagramUsername: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("links")
    val links: UserLinksDto?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("portfolio_url")
    val portfolioUrl: Any?,
    @SerializedName("profile_image")
    val profileImage: ProfileImageDto?,
    @SerializedName("social")
    val social: SocialDto?,
    @SerializedName("total_collections")
    val totalCollections: Int?,
    @SerializedName("total_likes")
    val totalLikes: Int?,
    @SerializedName("total_photos")
    val totalPhotos: Int?,
    @SerializedName("twitter_username")
    val twitterUsername: Any?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("username")
    val username: String?
)