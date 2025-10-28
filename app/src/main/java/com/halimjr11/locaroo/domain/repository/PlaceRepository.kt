package com.halimjr11.locaroo.domain.repository

import com.halimjr11.locaroo.domain.model.Place
import com.halimjr11.locaroo.domain.model.Result

interface PlaceRepository {
    suspend fun getPlaces(page: Int = 1, pageSize: Int = 20): Result<List<Place>>
    suspend fun getPlaceById(id: Long): Result<Place>
    suspend fun searchPlaces(query: String, page: Int = 1, pageSize: Int = 20): Result<List<Place>>
    suspend fun createPlace(place: Place): Result<Place>
    suspend fun updatePlace(place: Place): Result<Place>
    suspend fun deletePlace(id: Long): Result<Unit>
}
