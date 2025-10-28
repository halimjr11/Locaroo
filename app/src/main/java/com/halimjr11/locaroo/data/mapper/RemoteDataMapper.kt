package com.halimjr11.locaroo.data.mapper

import com.halimjr11.locaroo.data.remote.model.AuthDataResponse
import com.halimjr11.locaroo.data.remote.model.PlaceResponse
import com.halimjr11.locaroo.data.remote.model.ReviewResponse
import com.halimjr11.locaroo.domain.model.AuthDataDomain
import com.halimjr11.locaroo.domain.model.PlaceDomain
import com.halimjr11.locaroo.domain.model.ReviewDomain

interface RemoteDataMapper {
    suspend fun mapAuthToDomain(loginData: AuthDataResponse): AuthDataDomain
    suspend fun mapPlaceToDomain(placeData: PlaceResponse): PlaceDomain
    suspend fun mapReviewToDomain(reviewData: ReviewResponse): ReviewDomain
}