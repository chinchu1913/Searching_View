package com.example.search_image.domain.repository

import com.example.search_image.common.Resource
import com.example.search_image.domain.entities.ResultEntity
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getSearchResults(
        searchQuery: String,
        page: Int, limit: Int
    ): Flow<Resource<List<ResultEntity>>>
}