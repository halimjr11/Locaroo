package com.halimjr11.locaroo.domain.repository

import com.halimjr11.locaroo.domain.model.PlaceDomain
import com.halimjr11.locaroo.domain.utils.DomainResult

interface PlaceRemoteRepository {
    /**
     * Retrieves a list of places from the server.
     * If [city] is not null, it will filter the results to only include places that are in the specified city.
     * @param city The city to filter the results by. If null, will not filter by city.
     * @return A DomainResult containing a list of PlaceDomain objects if successful, or an ErrorResponse object if not.
     */
    suspend fun getPlaces(
        city: String? = null
    ): DomainResult<List<PlaceDomain>>

    /**
     * Retrieves a single place from the server by its ID.
     * @param id The ID of the place to retrieve.
     * @return A DomainResult containing the retrieved place if successful, or an ErrorResponse object if not.
     */
    suspend fun getPlaceById(id: Long): DomainResult<PlaceDomain>

    /**
     * Searches for places on the server that match a given query.
     * @param query The query to search for.
     * @param page The page number to retrieve. Defaults to 1.
     * @param pageSize The number of places to retrieve per page. Defaults to 20.
     * @return A list of places that match the given query, or an error if failed.
     */
    suspend fun searchPlaces(
        query: String,
        page: Int = 1,
        pageSize: Int = 20
    ): DomainResult<List<PlaceDomain>>

    /**
     * Adds a new place to the server.
     * @param name The name of the place.
     * @param description The description of the place.
     * @param latitude The latitude of the place.
     * @param longitude The longitude of the place.
     * @param location The location of the place.
     * @param tagsSlugs The tags of the place separated by commas.
     * @param imageUri The image of the place.
     * @return A DomainResult containing the added place if successful, or an ErrorResponse object if not.
     */
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
