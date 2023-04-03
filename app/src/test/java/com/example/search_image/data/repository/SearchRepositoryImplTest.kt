package com.example.search_image.data.repository

import com.example.search_image.common.Resource
import com.example.search_image.data.remote.SearchApi
import com.example.search_image.data.remote.dto.*
import com.example.search_image.domain.entities.*
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import java.io.IOException

class SearchRepositoryImplTest {
    private lateinit var mockSearchApi: SearchApi
    private lateinit var repository: SearchRepositoryImpl
    private lateinit var resultEntity: ResultEntity

    @Before
    fun setUp() {
        mockSearchApi = mock()
        repository = SearchRepositoryImpl(mockSearchApi)

        resultEntity = ResultEntity(
            altDescription = "A beautiful mountain view",
            blurHash = "LEHV6nWB2yk8pyo0adR*.7kCMdnj",
            color = "#735640",
            createdAt = "2022-03-25T14:15:16-04:00",
            description = "This is an amazing view of the mountains in Colorado.",
            height = 1080,
            id = "abc123",
            likedByUser = true,
            likes = 100,
            tags = listOf(
                TagEntity(
                    title = "mountain",
                    type = "search"
                ),
                TagEntity(
                    title = "nature",
                    type = "search"
                )
            ),
            updatedAt = "2022-03-25T14:15:16-04:00",
            urls = UrlsEntity(
                full = "https://images.unsplash.com/photo-1234567890",
                raw = "https://images.unsplash.com/photo-1234567890/raw",
                regular = "https://images.unsplash.com/photo-1234567890?ixid=...",
                small = "https://images.unsplash.com/photo-1234567890?ixid=...",
                smallS3 = "https://images.unsplash.com/photo-1234567890?ixid=...",
                thumb = "https://images.unsplash.com/photo-1234567890?ixid=..."
            ),
            user = UserEntity(
                acceptedTos = true,
                firstName = "John",
                forHire = false,
                id = "user123",
                instagramUsername = "john123",
                lastName = "Doe",
                links = UserLinksEntity(
                    followers = "https://api.unsplash.com/users/john123/followers",
                    following = "https://api.unsplash.com/users/john123/following",
                    html = "https://unsplash.com/@john123",
                    likes = "https://api.unsplash.com/users/john123/likes",
                    photos = "https://api.unsplash.com/users/john123/photos",
                    portfolio = "https://api.unsplash.com/users/john123/portfolio",
                    self = "https://api.unsplash.com/users/john123"
                ),
                location = "Colorado",
                name = "John Doe",
                profileImage = ProfileImageEntity(
                    large = "https://images.unsplash.com/profile-1234567890",
                    medium = "https://images.unsplash.com/profile-1234567890",
                    small = "https://images.unsplash.com/profile-1234567890"
                ),
                totalCollections = 10,
                totalLikes = 500,
                totalPhotos = 100,
                updatedAt = "2022-03-25T14:15:16-04:00",
                username = "john123"
            ),
            width = 1920
        )


    }

    private fun getResultDto() = ResultDto(
        altDescription = "A beautiful mountain view",
        blurHash = "LEHV6nWB2yk8pyo0adR*.7kCMdnj",
        color = "#735640",
        createdAt = "2022-03-25T14:15:16-04:00",
        description = "This is an amazing view of the mountains in Colorado.",
        height = 1080,
        id = "abc123",
        likedByUser = true,
        likes = 100,
        tags = listOf(
            TagDto(
                title = "mountain",
                type = "search",
                source = null
            ),
            TagDto(
                title = "nature",
                type = "search",
                source = null
            )
        ),
        updatedAt = "2022-03-25T14:15:16-04:00",
        urls = UrlsDto(
            full = "https://images.unsplash.com/photo-1234567890",
            raw = "https://images.unsplash.com/photo-1234567890/raw",
            regular = "https://images.unsplash.com/photo-1234567890?ixid=...",
            small = "https://images.unsplash.com/photo-1234567890?ixid=...",
            smallS3 = "https://images.unsplash.com/photo-1234567890?ixid=...",
            thumb = "https://images.unsplash.com/photo-1234567890?ixid=..."
        ),
        user = UserDto(
            acceptedTos = true,
            firstName = "John",
            forHire = false,
            id = "user123",
            instagramUsername = "john123",
            lastName = "Doe",
            links = UserLinksDto(
                followers = "https://api.unsplash.com/users/john123/followers",
                following = "https://api.unsplash.com/users/john123/following",
                html = "https://unsplash.com/@john123",
                likes = "https://api.unsplash.com/users/john123/likes",
                photos = "https://api.unsplash.com/users/john123/photos",
                portfolio = "https://api.unsplash.com/users/john123/portfolio",
                self = "https://api.unsplash.com/users/john123"
            ),
            location = "Colorado",
            name = "John Doe",
            profileImage = ProfileImageDto(
                large = "https://images.unsplash.com/profile-1234567890",
                medium = "https://images.unsplash.com/profile-1234567890",
                small = "https://images.unsplash.com/profile-1234567890"
            ),
            totalCollections = 10,
            totalLikes = 500,
            totalPhotos = 100,
            updatedAt = "2022-03-25T14:15:16-04:00",
            username = "john123",
            bio = null,
            portfolioUrl = null,
            twitterUsername = null,
            social = null
        ),
        width = 1920,
        currentUserCollections = null,
        links = null,
        promotedAt = null,
        sponsorship = null,
        topicSubmissions = null
    )

    private fun getDtoData() = SearchResultDto(
        results = listOf(
            getResultDto()
        ),
        total = 100,
        totalPages = 10
    )


    @Test
    fun `is loading is true on getSearchList`() = runBlocking {
        Mockito.`when`(mockSearchApi.getSearchList("cat", limit = 10, page = 1)).thenReturn(null)
        val firstItem = repository.getSearchResults("cat", limit = 10, page = 1).first()
        assertThat((firstItem as Resource.Loading).isLoading).isEqualTo(true)
    }

    @Test
    fun `getSearchList returns Success`() = runBlocking {
        // Given
        val expectedData = resultEntity
        val expectedResponse = getDtoData()

        Mockito.`when`(mockSearchApi.getSearchList("cat", 10, 1)).thenReturn(expectedResponse)


        val secondItem = repository
            .getSearchResults("cat", 1, 10).drop(1).first()
        assertThat(secondItem).isInstanceOf(Resource.Success::class.java)

        assertThat((secondItem as Resource.Success).data?.first()?.blurHash).isEqualTo(expectedData.blurHash)
        assertThat(secondItem.data?.first()?.id).isEqualTo(expectedData.id)
        assertThat(secondItem.data?.first()?.altDescription)
            .isEqualTo(expectedData.altDescription)
    }

    @Test
    fun `is status is instance of error`() = runBlocking {
        Mockito.`when`(mockSearchApi.getSearchList("cat", 10, 1)).thenAnswer {
            throw  IOException()
        }
        val secondItem = repository
            .getSearchResults("cat", 1, 10).drop(1).first()
        assertThat(secondItem).isInstanceOf(Resource.Error::class.java)

    }
}