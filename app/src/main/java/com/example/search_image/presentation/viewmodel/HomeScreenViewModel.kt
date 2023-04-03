package com.example.search_image.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.search_image.common.Resource
import com.example.search_image.common.utils.NetworkUtil
import com.example.search_image.domain.repository.SearchRepository
import com.example.search_image.presentation.SearchEvent
import com.example.search_image.presentation.SearchListingState
import com.example.search_image.presentation.viewmodel.HomeScreenViewModel.ViewModelConstants.FETCH_LIMIT
import com.example.search_image.presentation.viewmodel.HomeScreenViewModel.ViewModelConstants.INITIAL_PAGE_COUNT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: SearchRepository,
    private val networkUtils: NetworkUtil
) :
    ViewModel() {
    var state by mutableStateOf(SearchListingState())
    private var searchJob: Job? = null
    private var page: Int = INITIAL_PAGE_COUNT
    private var lastConnectionStatus = true

    init {
        viewModelScope.launch {
            observeNetworkConnection()
        }
    }

    private fun getSearchListing(
        query: String = state.searchQuery.lowercase(),
    ) {
        viewModelScope.launch {
            repository
                .getSearchResults(searchQuery = query, page, FETCH_LIMIT)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                state = state.copy(
                                    searchLists = if (page == INITIAL_PAGE_COUNT) {
                                        listings
                                    } else {
                                        state.searchLists + listings
                                    },
                                    isEmpty = (page == INITIAL_PAGE_COUNT && listings.isEmpty()),
                                    errorMessage = "",
                                    isLoading = false
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = if (state.searchLists.isEmpty()) {
                                state.copy(
                                    isLoading = false,
                                    isError = true,
                                    isEmpty = false,
                                    errorMessage = result.message ?: ""
                                )
                            } else {
                                state.copy(isLoading = false, isEmpty = false)
                            }
                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }


    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnSearchQueryChange -> {
                page = 1
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getSearchListing()
                }
            }
            is SearchEvent.GetMoreItems -> {
                if (state.searchLists.size >= FETCH_LIMIT && !state.isLoading) {
                    page += 1
                    getSearchListing()
                }
            }

        }
    }

    private suspend fun observeNetworkConnection() {
        networkUtils.getNetworkLiveData()
        networkUtils.getNetworkLiveData().asFlow().collect { isConnected ->
            //show the connection error is the connection status is disconnected
            state = state.copy(showNetworkUnavailable = !isConnected)
            //show the connection success message if the connection if disconnected and reconnected back.
            val isConnectionIsBack = !lastConnectionStatus && isConnected
            if (isConnectionIsBack) {
                viewModelScope.launch {
                    //Refresh the data once the connection is back
                    getSearchListing()
                    state = state.copy(showNetworkConnected = true)
                    delay(ViewModelConstants.CONNECTION_BACK_MSG_TIMEOUT)
                    state = state.copy(showNetworkConnected = false)
                }
            }
            lastConnectionStatus = isConnected
        }
    }

    object ViewModelConstants {
        const val FETCH_LIMIT = 10
        const val INITIAL_PAGE_COUNT = 1
        const val CONNECTION_BACK_MSG_TIMEOUT = 2000L

    }
}