package com.example.search_image.data.repository

import com.example.search_image.common.Resource
import com.example.search_image.data.remote.SearchApi
import com.example.search_image.domain.entities.ResultEntity
import com.example.search_image.domain.entities.toEntity
import com.example.search_image.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepositoryImpl @Inject constructor(
    private val api: SearchApi
) : SearchRepository {

    override suspend fun getSearchResults(
        searchQuery: String,
        page: Int, limit: Int
    ): Flow<Resource<List<ResultEntity>>> {
        return flow {
            emit(value = Resource.Loading(true))

            val remoteListings = try {
                val response = api.getSearchList(
                    searchQuery = searchQuery, limit = limit,
                    page = page
                )
                response
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("network error: ${e.code()}"))
                null
            }

            remoteListings?.let { listings ->
                emit(Resource.Success(
                    data = listings
                        .results.map {
                            it.toEntity()
                        }
                ))
                emit(
                    Resource.Loading(
                        isLoading = false
                    )
                )
                return@flow
            }

        }

    }

}