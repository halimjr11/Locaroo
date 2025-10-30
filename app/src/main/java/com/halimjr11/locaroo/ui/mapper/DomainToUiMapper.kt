package com.halimjr11.locaroo.ui.mapper

import com.halimjr11.locaroo.domain.model.*
import com.halimjr11.locaroo.ui.model.*

fun PlaceDomain.toUi(): PlaceUi = PlaceUi(
    id = id.toString(),
    name = name,
    location = location,
    description = description,
    imageUrl = imageUrl,
    rating = avgRating,
    reviewsCount = reviewsCount,
    latitude = latitude,
    longitude = longitude
)

fun ReviewDomain.toUi(): ReviewUi = ReviewUi(
    id = id,
    rating = rating,
    comment = comment,
    user = user.toUi(),
    createdAt = createdAt
)

fun AuthUserDomain.toUi(): AuthUserUi = AuthUserUi(
    id = id,
    username = username,
    email = email,
    name = name
)

fun ScheduleDomain.toUi(): ScheduleUi = ScheduleUi(
    id = id,
    name = name,
    location = location,
    imageUrl = imageUrl,
    date = date
)

fun ScheduleItem.toUi(): ScheduleItemUi = ScheduleItemUi(
    id = id,
    date = date,
    title = title,
    location = location
)

fun AuthDataDomain.toUi(): AuthDataUi = AuthDataUi(
    accessToken = accessToken,
    refreshToken = refreshToken,
    user = user.toUi()
)
