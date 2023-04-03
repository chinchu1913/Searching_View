package com.example.search_image.data.remote

import com.example.search_image.BuildConfig
import com.example.search_image.data.remote.dto.SearchResultDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchApi {
    @GET("search/photos")
    suspend fun getSearchList(
        @Query("query") searchQuery: String,
        @Query("per_page") limit: Int,
        @Query("page") page: Int,
        @Header("Authorization") apiKey: String = BuildConfig.API_KEY
    ): SearchResultDto
}