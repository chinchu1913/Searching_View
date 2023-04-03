package com.example.search_image.domain.entities

import android.os.Parcelable
import com.example.search_image.data.remote.dto.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserLinksEntity(
    val followers: String?,
    val following: String?,
    val html: String?,
    val likes: String?,
    val photos: String?,
    val portfolio: String?,
    val self: String?
) : Parcelable


@Parcelize
data class ProfileImageEntity(
    val large: String?,
    val medium: String?,
    val small: String?
) : Parcelable


@Parcelize
data class UserEntity(
    val acceptedTos: Boolean?,
    val firstName: String?,
    val forHire: Boolean?,
    val id: String?,
    val instagramUsername: String?,
    val lastName: String?,
    val links: UserLinksEntity?,
    val location: String?,
    val name: String?,
    val profileImage: ProfileImageEntity?,
    val totalCollections: Int?,
    val totalLikes: Int?,
    val totalPhotos: Int?,
    val updatedAt: String?,
    val username: String?
) : Parcelable

@Parcelize
data class UrlsEntity(
    val full: String?,
    val raw: String?,
    val regular: String?,
    val small: String?,
    val smallS3: String?,
    val thumb: String?
) : Parcelable

@Parcelize
data class TypeEntity(
    val prettySlug: String?,
    val slug: String?
) : Parcelable


@Parcelize
data class TagEntity(
    val title: String?,
    val type: String?
) : Parcelable


@Parcelize
data class SubcategoryEntity(
    val prettySlug: String?,
    val slug: String?
) : Parcelable


@Parcelize
data class SourceEntity(
    val ancestry: AncestryEntity?,
    val coverPhoto: CoverPhotoEntity?,
    val description: String?,
    val metaDescription: String?,
    val metaTitle: String?,
    val subtitle: String?,
    val title: String?
) : Parcelable


@Parcelize
data class ResultEntity(
    val altDescription: String?,
    val blurHash: String?,
    val color: String?,
    val createdAt: String?,
    val description: String?,
    val height: Int?,
    val id: String?,
    val likedByUser: Boolean?,
    val likes: Int?,
    val tags: List<TagEntity>,
    val updatedAt: String?,
    val urls: UrlsEntity?,
    val user: UserEntity?,
    val width: Int?
) : Parcelable


@Parcelize
data class CoverPhotoEntity(
    val altDescription: String?,
    val blurHash: String?,
    val color: String?,
    val createdAt: String?,
    val description: String?,
    val height: Int?,
    val id: String?,
    val likedByUser: Boolean?,
    val likes: Int?,
    val links: LinksEntity?,
    val premium: Boolean?,
    val promotedAt: String?,
    val updatedAt: String?,
    val urls: UrlsEntity?,
    val user: UserEntity?,
    val width: Int?
) : Parcelable

@Parcelize
data class AncestryEntity(
    val category: CategoryEntity?,
    val subcategory: SubcategoryEntity?,
    val type: TypeEntity?
) : Parcelable


@Parcelize
data class LinksEntity(
    val download: String?,
    val downloadLocation: String?,
    val html: String?,
    val self: String?
) : Parcelable

@Parcelize
data class CategoryEntity(
    val prettySlug: String?,
    val slug: String?
) : Parcelable


fun UrlsDto.toEntity(): UrlsEntity {
    return UrlsEntity(
        full = full,
        raw = raw,
        regular = regular,
        small = small,
        smallS3 = smallS3,
        thumb = thumb
    )
}

fun List<TagEntity>.getTagTitles(): String {
    return this.joinToString(separator = ", ") { it.title ?: "" }
}

fun UserDto.toEntity(): UserEntity {
    return UserEntity(
        acceptedTos = this.acceptedTos,
        firstName = this.firstName,
        forHire = this.forHire,
        id = this.id,
        instagramUsername = this.instagramUsername,
        lastName = this.lastName,
        links = this.links?.toEntity(),
        location = this.location,
        name = this.name,
        profileImage = this.profileImage?.toEntity(),
        totalCollections = this.totalCollections,
        totalLikes = this.totalLikes,
        totalPhotos = this.totalPhotos,
        updatedAt = this.updatedAt,
        username = this.username
    )
}


fun UserLinksDto.toEntity(): UserLinksEntity {
    return UserLinksEntity(
        followers = this.followers,
        following = this.following,
        html = this.html,
        likes = this.likes,
        photos = this.photos,
        portfolio = this.portfolio,
        self = this.self
    )
}

fun TagDto.toEntity(): TagEntity {
    return TagEntity(
        title = this.title,
        type = this.type
    )
}


fun ProfileImageDto.toEntity(): ProfileImageEntity {
    return ProfileImageEntity(
        large = this.large,
        medium = this.medium,
        small = this.small
    )
}


fun ResultDto.toEntity(): ResultEntity {
    return ResultEntity(
        altDescription = altDescription,
        blurHash = blurHash,
        color = color,
        createdAt = createdAt,
        description = description,
        height = height,
        id = id,
        likedByUser = likedByUser,
        likes = likes,
        tags = tags.map { it.toEntity() },
        updatedAt = updatedAt,
        urls = urls?.toEntity(),
        user = user?.toEntity(),
        width = width
    )
}