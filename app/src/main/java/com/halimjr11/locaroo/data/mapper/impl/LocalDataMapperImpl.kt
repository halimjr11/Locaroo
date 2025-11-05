package com.halimjr11.locaroo.data.mapper.impl

import com.halimjr11.locaroo.common.orDoubleZero
import com.halimjr11.locaroo.common.orLongZero
import com.halimjr11.locaroo.data.local.model.FavoriteEntity
import com.halimjr11.locaroo.data.local.model.ScheduleEntity
import com.halimjr11.locaroo.data.mapper.LocalDataMapper
import com.halimjr11.locaroo.domain.model.PlaceDomain
import com.halimjr11.locaroo.domain.model.ScheduleDomain
import javax.inject.Inject

class LocalDataMapperImpl @Inject constructor() : LocalDataMapper {
    override suspend fun mapScheduleToDomain(scheduleEntity: ScheduleEntity): ScheduleDomain {
        return ScheduleDomain(
            id = scheduleEntity.id.orLongZero(),
            name = scheduleEntity.name.orEmpty(),
            date = scheduleEntity.date.orEmpty(),
            imageUrl = scheduleEntity.imageUrl.orEmpty(),
            location = scheduleEntity.location.orEmpty(),
        )
    }

    override suspend fun mapScheduleToEntity(scheduleDomain: ScheduleDomain): ScheduleEntity {
        return ScheduleEntity(
            id = scheduleDomain.id,
            name = scheduleDomain.name,
            date = scheduleDomain.date,
            imageUrl = scheduleDomain.imageUrl,
            location = scheduleDomain.location,
        )
    }

    override suspend fun mapFavoriteToDomain(favoriteEntity: FavoriteEntity): PlaceDomain {
        return PlaceDomain(
            id = favoriteEntity.id.orLongZero(),
            name = favoriteEntity.name.orEmpty(),
            location = favoriteEntity.location.orEmpty(),
            avgRating = favoriteEntity.rating.orDoubleZero(),
            imageUrl = favoriteEntity.imageUrl.orEmpty(),
        )
    }

    override suspend fun mapFavoriteToEntity(placeDomain: PlaceDomain): FavoriteEntity {
        return FavoriteEntity(
            id = placeDomain.id,
            name = placeDomain.name,
            rating = placeDomain.avgRating,
            location = placeDomain.location,
            imageUrl = placeDomain.imageUrl,
        )
    }
}