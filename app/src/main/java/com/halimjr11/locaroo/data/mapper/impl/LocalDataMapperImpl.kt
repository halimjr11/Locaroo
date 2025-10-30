package com.halimjr11.locaroo.data.mapper.impl

import com.halimjr11.locaroo.common.orLongZero
import com.halimjr11.locaroo.data.local.model.ScheduleEntity
import com.halimjr11.locaroo.data.mapper.LocalDataMapper
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
}