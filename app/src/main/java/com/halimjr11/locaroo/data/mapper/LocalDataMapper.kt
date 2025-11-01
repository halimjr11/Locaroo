package com.halimjr11.locaroo.data.mapper

import com.halimjr11.locaroo.data.local.model.FavoriteEntity
import com.halimjr11.locaroo.data.local.model.ScheduleEntity
import com.halimjr11.locaroo.domain.model.PlaceDomain
import com.halimjr11.locaroo.domain.model.ScheduleDomain

interface LocalDataMapper {
    /**
     * Maps a [ScheduleEntity] to a [ScheduleDomain].
     *
     * @param scheduleEntity The [ScheduleEntity] to be mapped.
     * @return A [ScheduleDomain] mapped from the given [ScheduleEntity].
     */
    suspend fun mapScheduleToDomain(scheduleEntity: ScheduleEntity): ScheduleDomain

    /**
     * Maps a [ScheduleDomain] to a [ScheduleEntity].
     *
     * @param scheduleDomain The [ScheduleDomain] to be mapped.
     * @return A [ScheduleEntity] mapped from the given [ScheduleDomain].
     */
    suspend fun mapScheduleToEntity(scheduleDomain: ScheduleDomain): ScheduleEntity

    /**
     * Maps a [FavoriteEntity] to a [PlaceDomain].
     *
     * This function takes a [FavoriteEntity] and maps its fields to a [PlaceDomain].
     * The returned [PlaceDomain] will contain the same information as the given
     * [FavoriteEntity].
     *
     * @param favoriteEntity The [FavoriteEntity] to be mapped.
     * @return A [PlaceDomain] mapped from the given [FavoriteEntity].
     */
    suspend fun mapFavoriteToDomain(favoriteEntity: FavoriteEntity): PlaceDomain


    /**
     * Maps a [PlaceDomain] to a [FavoriteEntity].
     *
     * @param placeDomain The [PlaceDomain] to be mapped.
     * @return A [FavoriteEntity] mapped from the given [PlaceDomain].
     */
    suspend fun mapFavoriteToEntity(placeDomain: PlaceDomain): FavoriteEntity
}
