package com.halimjr11.locaroo.data.mapper.impl

import com.halimjr11.locaroo.common.orDoubleZero
import com.halimjr11.locaroo.common.orLongZero
import com.halimjr11.locaroo.common.orZero
import com.halimjr11.locaroo.data.mapper.RemoteDataMapper
import com.halimjr11.locaroo.data.remote.model.AuthDataResponse
import com.halimjr11.locaroo.data.remote.model.PlaceResponse
import com.halimjr11.locaroo.data.remote.model.ReviewResponse
import com.halimjr11.locaroo.domain.model.AuthDataDomain
import com.halimjr11.locaroo.domain.model.AuthUserDomain
import com.halimjr11.locaroo.domain.model.PlaceDomain
import com.halimjr11.locaroo.domain.model.ReviewDomain

class RemoteDataMapperImpl : RemoteDataMapper {
    override suspend fun mapAuthToDomain(loginData: AuthDataResponse): AuthDataDomain {
        return AuthDataDomain(
            accessToken = loginData.accessToken.orEmpty(),
            refreshToken = loginData.refreshToken.orEmpty(),
            user = loginData.user?.let { user ->
                AuthUserDomain(
                    id = user.id.orLongZero(),
                    username = user.username.orEmpty(),
                    email = user.email.orEmpty(),
                    name = user.name.orEmpty()
                )
            } ?: AuthUserDomain()
        )
    }

    override suspend fun mapPlaceToDomain(placeData: PlaceResponse): PlaceDomain {
        return PlaceDomain(
            id = placeData.id.orLongZero(),
            name = placeData.name.orEmpty(),
            description = placeData.description.orEmpty(),
            location = placeData.location.orEmpty(),
            latitude = placeData.latitude.orDoubleZero(),
            longitude = placeData.longitude.orDoubleZero(),
            tagsSlugs = placeData.tagsSlugs.orEmpty(),
            userId = placeData.userId.orLongZero(),
            avgRating = placeData.avgRating.orDoubleZero(),
            reviewsCount = placeData.reviewsCount.orZero(),
            imageUrl = placeData.imageUrl.orEmpty(),
            createdAt = placeData.createdAt.orEmpty(),
            updatedAt = placeData.updatedAt.orEmpty()
        )
    }

    override suspend fun mapReviewToDomain(reviewData: ReviewResponse): ReviewDomain {
        return ReviewDomain(
            id = reviewData.id.orLongZero(),
            rating = reviewData.rating.orZero(),
            comment = reviewData.comment.orEmpty(),
            user = reviewData.user?.let { user ->
                AuthUserDomain(
                    id = user.id.orLongZero(),
                    username = user.username.orEmpty(),
                    email = user.email.orEmpty(),
                    name = user.name.orEmpty()
                )
            } ?: AuthUserDomain(),
            placeId = reviewData.placeId.orLongZero(),
            createdAt = reviewData.createdAt.orEmpty(),
            updatedAt = reviewData.updatedAt.orEmpty()
        )
    }
}