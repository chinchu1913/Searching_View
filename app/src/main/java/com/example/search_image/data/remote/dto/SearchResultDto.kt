package com.example.search_image.data.remote.dto


import com.google.gson.annotations.SerializedName

data class SearchResultDto(
    @SerializedName("results")
    val results: List<ResultDto>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)