package com.example.search_image.data.remote.dto


import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("pretty_slug")
    val prettySlug: String?,
    @SerializedName("slug")
    val slug: String?
)