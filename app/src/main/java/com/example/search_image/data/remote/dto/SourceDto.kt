package com.example.search_image.data.remote.dto


import com.google.gson.annotations.SerializedName

data class SourceDto(
    @SerializedName("ancestry")
    val ancestry: AncestryDto?,
    @SerializedName("cover_photo")
    val coverPhoto: CoverPhotoDto?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("meta_description")
    val metaDescription: String?,
    @SerializedName("meta_title")
    val metaTitle: String?,
    @SerializedName("subtitle")
    val subtitle: String?,
    @SerializedName("title")
    val title: String?
)