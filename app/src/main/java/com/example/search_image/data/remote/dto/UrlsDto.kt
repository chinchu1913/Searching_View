package com.example.search_image.data.remote.dto


import com.google.gson.annotations.SerializedName

data class UrlsDto(
    @SerializedName("full")
    val full: String?,
    @SerializedName("raw")
    val raw: String?,
    @SerializedName("regular")
    val regular: String?,
    @SerializedName("small")
    val small: String?,
    @SerializedName("small_s3")
    val smallS3: String?,
    @SerializedName("thumb")
    val thumb: String?
)