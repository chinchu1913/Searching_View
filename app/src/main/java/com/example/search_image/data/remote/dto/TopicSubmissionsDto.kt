package com.example.search_image.data.remote.dto


import com.google.gson.annotations.SerializedName

data class TopicSubmissionsDto(
    @SerializedName("animals")
    val animals: AnimalsDto?
)