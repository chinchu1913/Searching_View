package com.example.search_image.data.remote.dto


import com.google.gson.annotations.SerializedName

data class LinksDto(
    @SerializedName("download")
    val download: String?,
    @SerializedName("download_location")
    val downloadLocation: String?,
    @SerializedName("html")
    val html: String?,
    @SerializedName("self")
    val self: String?
)