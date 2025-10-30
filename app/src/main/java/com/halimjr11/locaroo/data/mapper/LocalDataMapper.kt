package com.halimjr11.locaroo.data.mapper

import com.halimjr11.locaroo.data.local.model.ScheduleEntity
import com.halimjr11.locaroo.domain.model.ScheduleDomain

interface LocalDataMapper {
    suspend fun mapScheduleToDomain(scheduleEntity: ScheduleEntity): ScheduleDomain
    suspend fun mapScheduleToEntity(scheduleDomain: ScheduleDomain): ScheduleEntity
}