package com.halimjr11.locaroo.domain.repository

import com.halimjr11.locaroo.domain.model.PlaceDomain
import com.halimjr11.locaroo.domain.model.ScheduleDomain
import kotlinx.coroutines.flow.Flow

interface PlaceLocalRepository {
    /**
     * Retrieves all places from the database that match a given date.
     * @param date The date to search for, in the format "yyyy-MM-dd".
     * @return A list of all places in the database that match the given date.
     */
    suspend fun getPlaces(date: String): List<ScheduleDomain>

    /**
     * Inserts a place into the database.
     * @param place The place to be inserted.
     * @param date The date to which the place belongs, in the format "yyyy-MM-dd".
     */
    suspend fun insertPlace(place: PlaceDomain, date: String)

    /**
     * Deletes a place from the database.
     * @param id The id of the place to be deleted.
     */
    suspend fun deletePlace(id: Long)

    /**
     * Checks if a place is favorited.
     * @param id The id of the place to check.
     * @return true if the place is favorited, false otherwise.
     */
    suspend fun isFavorite(id: Long): Boolean

    /**
     * Inserts a place into the database.
     * @param place The place to be inserted.
     * @param date The date to which the place belongs, in the format "yyyy-MM-dd".
     */
    suspend fun insertFavorite(place: PlaceDomain)

    /**
     * Deletes a place from the database.
     * @param id The id of the place to be deleted.
     */
    suspend fun deleteFavorite(id: Long)

    /**
     * Retrieves all favorite places from the database.
     * @return A list of all favorite places in the database.
     */
    suspend fun getFavorites(): Flow<List<PlaceDomain>>
}