package com.halimjr11.locaroo.domain.repository

import com.halimjr11.locaroo.domain.utils.DomainResult
import com.halimjr11.locaroo.domain.model.PlaceDomain

interface PlaceRepository {
    suspend fun getPlaces(page: Int = 1, pageSize: Int = 20): DomainResult<List<PlaceDomain>>
    suspend fun getPlaceById(id: Long): DomainResult<PlaceDomain>
    suspend fun searchPlaces(
        query: String,
        page: Int = 1,
        pageSize: Int = 20
    ): DomainResult<List<PlaceDomain>>

    suspend fun addPlace(
        name: String,
        description: String,
        latitude: Double,
        longitude: Double,
        location: String,
        tagsSlugs: List<String>,
        imageUri: String
    ): DomainResult<PlaceDomain>
}
