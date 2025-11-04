package com.halimjr11.locaroo.ui.mapper.impl

import com.halimjr11.locaroo.domain.model.AuthUserDomain
import com.halimjr11.locaroo.domain.model.PlaceDomain
import com.halimjr11.locaroo.domain.model.ReviewDomain
import com.halimjr11.locaroo.domain.model.ScheduleDomain
import com.halimjr11.locaroo.ui.mapper.UiDataMapper
import com.halimjr11.locaroo.ui.model.AuthUserUi
import com.halimjr11.locaroo.ui.model.PlaceUi
import com.halimjr11.locaroo.ui.model.ReviewUi
import com.halimjr11.locaroo.ui.model.ScheduleItemUi
import com.halimjr11.locaroo.ui.model.ScheduleUi
import javax.inject.Inject

class UiDataMapperImpl @Inject constructor() : UiDataMapper {
    override suspend fun mapUsertoUI(domain: AuthUserDomain): AuthUserUi {
        return AuthUserUi(
            id = domain.id,
            username = domain.username,
            email = domain.email,
            name = domain.name
        )
    }

    override suspend fun mapScheduleToUI(domain: ScheduleDomain): ScheduleUi {
        return ScheduleUi(
            id = domain.id,
            name = domain.name,
            location = domain.location,
            imageUrl = domain.imageUrl,
            date = domain.date
        )
    }

    override suspend fun mapScheduleItemToUI(domain: ScheduleUi): ScheduleItemUi {
        return ScheduleItemUi(
            id = domain.id,
            date = domain.date,
            title = domain.name,
            location = domain.location
        )
    }

    override suspend fun mapPlaceToUI(domain: PlaceDomain): PlaceUi {
        return PlaceUi(
            id = domain.id,
            name = domain.name,
            location = domain.location,
            description = domain.description,
            imageUrl = domain.imageUrl,
            rating = domain.avgRating,
            reviewsCount = domain.reviewsCount,
            latitude = domain.latitude,
            longitude = domain.longitude
        )
    }

    override suspend fun mapReviewToUI(domain: ReviewDomain): ReviewUi {
        return ReviewUi(
            id = domain.id,
            rating = domain.rating,
            comment = domain.comment,
            user = mapUsertoUI(domain.user),
            createdAt = domain.createdAt
        )
    }
}
