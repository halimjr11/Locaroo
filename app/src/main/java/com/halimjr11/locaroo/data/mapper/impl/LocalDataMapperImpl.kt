package com.halimjr11.locaroo.data.mapper.impl

import com.halimjr11.locaroo.common.orLongZero
import com.halimjr11.locaroo.data.local.model.FavoriteEntity
import com.halimjr11.locaroo.data.local.model.ScheduleEntity
import com.halimjr11.locaroo.data.mapper.LocalDataMapper
import com.halimjr11.locaroo.domain.model.PlaceDomain
import com.halimjr11.locaroo.domain.model.ScheduleDomain

class LocalDataMapperImpl : LocalDataMapper {
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
            id = scheduleDomain.id.orLongZero(),
            name = scheduleDomain.name.orEmpty(),
            date = scheduleDomain.date.orEmpty(),
            imageUrl = scheduleDomain.imageUrl.orEmpty(),
            location = scheduleDomain.location.orEmpty(),
        )
    }

    override suspend fun mapFavoriteToDomain(favoriteEntity: FavoriteEntity): PlaceDomain {
        return PlaceDomain(
            id = favoriteEntity.id.orLongZero(),
            name = favoriteEntity.name.orEmpty(),
            location = favoriteEntity.location.orEmpty(),
            imageUrl = favoriteEntity.imageUrl.orEmpty(),
        )
    }

    override suspend fun mapFavoriteToEntity(placeDomain: PlaceDomain): FavoriteEntity {
        return FavoriteEntity(
            id = placeDomain.id.orLongZero(),
            name = placeDomain.name.orEmpty(),
            location = placeDomain.location.orEmpty(),
            imageUrl = placeDomain.imageUrl.orEmpty(),
        )
    }
}