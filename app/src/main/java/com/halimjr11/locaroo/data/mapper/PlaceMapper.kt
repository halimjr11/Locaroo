package com.halimjr11.locaroo.data.mapper

import com.halimjr11.locaroo.data.remote.model.PlaceDto
import com.halimjr11.locaroo.data.remote.model.PlaceCreateResponse
import com.halimjr11.locaroo.data.remote.model.ReviewDto
import com.halimjr11.locaroo.data.remote.model.ReviewUserDto
import com.halimjr11.locaroo.domain.model.Place
import com.halimjr11.locaroo.domain.model.Review
import com.halimjr11.locaroo.domain.model.ReviewUser
import javax.inject.Inject

class PlaceMapper @Inject constructor() {

    fun mapToDomain(dto: PlaceDto): Place {
        return Place(
            id = dto.id,
            name = dto.name,
            location = dto.location,
            description = dto.description,
            imageUrl = dto.imageUrl,
            latitude = dto.latitude,
            longitude = dto.longitude,
            tagsSlugs = dto.tagsSlugs,
            userId = dto.userId,
            avgRating = dto.avgRating,
            reviewsCount = dto.reviewsCount,
            createdAt = dto.createdAt,
            updatedAt = dto.updatedAt
        )
    }

    fun mapToDto(place: Place): PlaceDto {
        return PlaceDto(
            id = place.id,
            name = place.name,
            location = place.location,
            description = place.description,
            imageUrl = place.imageUrl,
            latitude = place.latitude,
            longitude = place.longitude,
            tagsSlugs = place.tagsSlugs,
            userId = place.userId,
            avgRating = place.avgRating,
            reviewsCount = place.reviewsCount,
            createdAt = place.createdAt,
            updatedAt = place.updatedAt
        )
    }

    fun mapCreateResponseToDomain(dto: PlaceCreateResponse): Place {
        return Place(
            id = dto.id,
            name = dto.name,
            location = dto.location,
            description = dto.description,
            imageUrl = dto.imageUrl,
            latitude = dto.latitude,
            longitude = dto.longitude,
            tagsSlugs = dto.tagsSlugs,
            userId = dto.userId,
            avgRating = dto.avgRating,
            reviewsCount = dto.reviewsCount,
            createdAt = dto.createdAt,
            updatedAt = dto.updatedAt
        )
    }
}

class ReviewMapper @Inject constructor() {

    fun mapToDomain(dto: ReviewDto): Review {
        return Review(
            id = dto.id,
            rating = dto.rating,
            comment = dto.comment,
            user = dto.user?.let { mapUserToDomain(it) },
            placeId = dto.placeId,
            createdAt = dto.createdAt,
            updatedAt = dto.updatedAt
        )
    }

    fun mapToDto(review: Review): ReviewDto {
        return ReviewDto(
            id = review.id,
            rating = review.rating,
            comment = review.comment,
            user = review.user?.let { mapUserToDto(it) },
            placeId = review.placeId,
            createdAt = review.createdAt,
            updatedAt = review.updatedAt
        )
    }

    private fun mapUserToDomain(dto: ReviewUserDto): ReviewUser {
        return ReviewUser(
            id = dto.id,
            name = dto.name,
            email = dto.email
        )
    }

    private fun mapUserToDto(user: ReviewUser): ReviewUserDto {
        return ReviewUserDto(
            id = user.id,
            name = user.name,
            email = user.email
        )
    }
}

// Extension functions for easier mapping
fun PlaceDto.toDomain() = PlaceMapper().mapToDomain(this)
fun Place.toDto() = PlaceMapper().mapToDto(this)
fun PlaceCreateResponse.toDomain() = PlaceMapper().mapCreateResponseToDomain(this)

fun ReviewDto.toDomain() = ReviewMapper().mapToDomain(this)
fun Review.toDto() = ReviewMapper().mapToDto(this)
