package com.example.search_image.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AnimalsDto(
    @SerializedName("approved_on")
    val approvedOn: String?,
    @SerializedName("status")
    val status: String?
)