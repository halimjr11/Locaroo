package com.halimjr11.locaroo.data.repository

import com.halimjr11.locaroo.common.coroutines.CoroutinesDispatcherProvider
import com.halimjr11.locaroo.data.local.database.ScheduleDao
import com.halimjr11.locaroo.data.mapper.LocalDataMapper
import com.halimjr11.locaroo.domain.model.PlaceDomain
import com.halimjr11.locaroo.domain.model.ScheduleDomain
import com.halimjr11.locaroo.domain.repository.PlaceLocalRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaceLocalRepositoryImpl @Inject constructor(
    private val dao: ScheduleDao,
    private val dispatcher: CoroutinesDispatcherProvider,
    private val mapper: LocalDataMapper
) : PlaceLocalRepository {
    override suspend fun getPlaces(
        date: String
    ): List<ScheduleDomain> = withContext(dispatcher.io) {
        dao.getByDate(date).map { mapper.mapScheduleToDomain(it) }
    }

    override suspend fun insertPlace(
        place: PlaceDomain,
        date: String
    ) = withContext(dispatcher.io) {
        val schedule = ScheduleDomain(
            id = place.id,
            name = place.name,
            imageUrl = place.imageUrl,
            location = place.location,
            date = date
        )
        dao.insert(schedule = mapper.mapScheduleToEntity(scheduleDomain = schedule))
    }

    override suspend fun deletePlace(id: Long) = withContext(dispatcher.io) {
        dao.delete(id)
    }
}