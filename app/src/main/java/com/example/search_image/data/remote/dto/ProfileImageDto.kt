package com.example.search_image.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ProfileImageDto(
    @SerializedName("large")
    val large: String?,
    @SerializedName("medium")
    val medium: String?,
    @SerializedName("small")
    val small: String?
)