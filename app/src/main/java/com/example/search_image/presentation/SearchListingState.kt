package com.example.search_image.presentation

import com.example.search_image.domain.entities.ResultEntity

data class SearchListingState(
    val searchLists: List<ResultEntity> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val showNetworkUnavailable: Boolean = false,
    val showNetworkConnected: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val isEmpty: Boolean = true,
)
