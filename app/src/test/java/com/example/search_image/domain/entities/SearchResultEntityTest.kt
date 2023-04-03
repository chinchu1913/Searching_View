package com.example.search_image.domain.entities

import com.example.search_image.data.remote.dto.ProfileImageDto
import com.example.search_image.data.remote.dto.UrlsDto
import com.example.search_image.data.remote.dto.UserDto
import com.example.search_image.data.remote.dto.UserLinksDto
import org.junit.Assert.assertEquals

import org.junit.Test

class EntityTests {

    @Test
    fun testUrlsDto_toEntity() {
        val urlsDto = UrlsDto(
            full = "https://example.com/full.jpg",
            raw = "https://example.com/raw.jpg",
            regular = "https://example.com/regular.jpg",
            small = "https://example.com/small.jpg",
            smallS3 = "https://example.com/small_s3.jpg",
            thumb = "https://example.com/thumb.jpg"
        )

        val expectedEntity = UrlsEntity(
            full = "https://example.com/full.jpg",
            raw = "https://example.com/raw.jpg",
            regular = "https://example.com/regular.jpg",
            small = "https://example.com/small.jpg",
            smallS3 = "https://example.com/small_s3.jpg",
            thumb = "https://example.com/thumb.jpg"
        )

        assertEquals(expectedEntity, urlsDto.toEntity())
    }

    @Test
    fun testUserDto_toEntity() {
        val userDto = UserDto(
            acceptedTos = true,
            firstName = "John",
            forHire = false,
            id = "1234",
            instagramUsername = "john_doe",
            lastName = "Doe",
            links = UserLinksDto(
                followers = "https://example.com/followers",
                following = "https://example.com/following",
                html = "https://example.com/html",
                likes = "https://example.com/likes",
                photos = "https://example.com/photos",
                portfolio = "https://example.com/portfolio",
                self = "https://example.com/self"
            ),
            location = "New York",
            name = "John Doe",
            profileImage = ProfileImageDto(
                large = "https://example.com/large.jpg",
                medium = "https://example.com/medium.jpg",
                small = "https://example.com/small.jpg"
            ),
            totalCollections = 5,
            totalLikes = 10,
            totalPhotos = 15,
            updatedAt = "2022-03-31T23:59:59Z",
            username = "johndoe",
            bio = null,
            portfolioUrl = null,
            social = null,
            twitterUsername = null,
        )

        val expectedEntity = UserEntity(
            acceptedTos = true,
            firstName = "John",
            forHire = false,
            id = "1234",
            instagramUsername = "john_doe",
            lastName = "Doe",
            links = UserLinksEntity(
                followers = "https://example.com/followers",
                following = "https://example.com/following",
                html = "https://example.com/html",
                likes = "https://example.com/likes",
                photos = "https://example.com/photos",
                portfolio = "https://example.com/portfolio",
                self = "https://example.com/self"
            ),
            location = "New York",
            name = "John Doe",
            profileImage = ProfileImageEntity(
                large = "https://example.com/large.jpg",
                medium = "https://example.com/medium.jpg",
                small = "https://example.com/small.jpg"
            ),
            totalCollections = 5,
            totalLikes = 10,
            totalPhotos = 15,
            updatedAt = "2022-03-31T23:59:59Z",
            username = "johndoe"
        )

        assertEquals(expectedEntity, userDto.toEntity())
    }

    @Test
    fun testGetTagTitlesWithEmptyList() {
        val tags: List<TagEntity> = emptyList()
        val result = tags.getTagTitles()
        assertEquals("", result)
    }

    @Test
    fun testGetTagTitlesWithOneTag() {
        val tags = listOf(TagEntity("Nature", "search"))
        val result = tags.getTagTitles()
        assertEquals("Nature", result)
    }

    @Test
    fun testGetTagTitlesWithMultipleTags() {
        val tags = listOf(
            TagEntity("Nature", "search"),
            TagEntity("Mountains", "search"),
            TagEntity("Landscape", "search")
        )
        val result = tags.getTagTitles()
        assertEquals("Nature, Mountains, Landscape", result)
    }

    @Test
    fun testToEntityWithAllParamsNull() {
        val linksDto = UserLinksDto(null, null, null, null, null, null, null)
        val result = linksDto.toEntity()
        assertEquals(UserLinksEntity(null, null, null, null, null, null, null), result)
    }

    @Test
    fun testToEntityWithAllParamsNonNull() {
        val linksDto = UserLinksDto(
            "http://example.com/followers",
            "http://example.com/following",
            "http://example.com/html",
            "http://example.com/likes",
            "http://example.com/photos",
            "http://example.com/portfolio",
            "http://example.com/self"
        )
        val result = linksDto.toEntity()
        assertEquals(
            UserLinksEntity(
                "http://example.com/followers",
                "http://example.com/following",
                "http://example.com/html",
                "http://example.com/likes",
                "http://example.com/photos",
                "http://example.com/portfolio",
                "http://example.com/self"
            ), result
        )
    }

    @Test
    fun testToEntityWithSomeParamsNull() {
        val linksDto = UserLinksDto(
            null,
            "http://example.com/following",
            "http://example.com/html",
            "http://example.com/likes",
            null,
            "http://example.com/portfolio",
            null
        )
        val result = linksDto.toEntity()
        assertEquals(
            UserLinksEntity(
                null,
                "http://example.com/following",
                "http://example.com/html",
                "http://example.com/likes",
                null,
                "http://example.com/portfolio",
                null
            ), result
        )
    }


}
