package com.halimjr11.locaroo.ui.mapper

import com.halimjr11.locaroo.domain.model.AuthUserDomain
import com.halimjr11.locaroo.domain.model.PlaceDomain
import com.halimjr11.locaroo.domain.model.ReviewDomain
import com.halimjr11.locaroo.domain.model.ScheduleDomain
import com.halimjr11.locaroo.ui.model.AuthUserUi
import com.halimjr11.locaroo.ui.model.PlaceUi
import com.halimjr11.locaroo.ui.model.ReviewUi
import com.halimjr11.locaroo.ui.model.ScheduleItemUi
import com.halimjr11.locaroo.ui.model.ScheduleUi

interface UiDataMapper {
    suspend fun mapUsertoUI(domain: AuthUserDomain): AuthUserUi
    suspend fun mapScheduleToUI(domain: ScheduleDomain): ScheduleUi
    suspend fun mapScheduleItemToUI(domain: ScheduleUi): ScheduleItemUi
    suspend fun mapPlaceToUI(domain: PlaceDomain): PlaceUi
    suspend fun mapReviewToUI(domain: ReviewDomain): ReviewUi
}