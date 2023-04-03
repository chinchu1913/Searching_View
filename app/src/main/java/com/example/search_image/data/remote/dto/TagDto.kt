package com.example.search_image.data.remote.dto


import com.google.gson.annotations.SerializedName

data class TagDto(
    @SerializedName("source")
    val source: SourceDto?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?
)