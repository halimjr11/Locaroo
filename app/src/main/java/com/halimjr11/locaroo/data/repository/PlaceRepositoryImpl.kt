package com.halimjr11.locaroo.data.repository

import com.halimjr11.locaroo.data.mapper.PlaceMapper
import com.halimjr11.locaroo.data.remote.LocalGemApi
import com.halimjr11.locaroo.data.remote.model.ApiResponse
import com.halimjr11.locaroo.domain.model.Place
import com.halimjr11.locaroo.domain.model.Result
import com.halimjr11.locaroo.domain.repository.PlaceRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val api: LocalGemApi,
    private val placeMapper: PlaceMapper
) : PlaceRepository, BaseRepository() {

    override suspend fun getPlaces(page: Int, pageSize: Int): Result<List<Place>> {
        return safeApiCall {
            val response = api.getPlaces()
            if (response.isSuccess) {
                response.data?.map { it.toDomain() } ?: emptyList()
            } else {
                throw Exception(response.message ?: "Failed to fetch places")
            }
        }
    }

    override suspend fun getPlaceById(id: Long): Result<Place> {
        return safeApiCall {
            val response = api.getPlace(id)
            if (response.isSuccess) {
                response.data?.toDomain() ?: throw Exception("Place not found")
            } else {
                throw Exception(response.message ?: "Failed to fetch place")
            }
        }
    }

    override suspend fun searchPlaces(query: String, page: Int, pageSize: Int): Result<List<Place>> {
        // Note: The API doesn't have a search endpoint yet
        // For now, we'll filter the places locally
        return safeApiCall {
            val response = api.getPlaces()
            if (response.isSuccess) {
                val places = response.data?.map { it.toDomain() } ?: emptyList()
                places.filter { 
                    it.name?.contains(query, ignoreCase = true) == true ||
                    it.description?.contains(query, ignoreCase = true) == true
                }
            } else {
                throw Exception(response.message ?: "Search failed")
            }
        }
    }

    override suspend fun createPlace(place: Place): Result<Place> {
        return safeApiCall {
            // Convert place to multipart/form-data
            val name = place.name?.toRequestBody() ?: throw IllegalArgumentException("Name is required")
            val location = place.location?.toRequestBody() ?: throw IllegalArgumentException("Location is required")
            val description = place.description?.toRequestBody()
            val latitude = place.latitude?.toString()?.toRequestBody()
            val longitude = place.longitude?.toString()?.toRequestBody()
            val tagsSlugs = place.tagsSlugs?.joinToString(",")?.toRequestBody()
            
            // For the image, you'll need to handle file upload
            // This is a simplified example - you'll need to provide the actual image file
            val imagePart = place.imageUrl?.let { 
                val file = File(it)
                MultipartBody.Part.createFormData(
                    "image",
                    file.name,
                    okhttp3.RequestBody.create(
                        okhttp3.MediaType.parse("image/*"),
                        file
                    )
                )
            } ?: throw IllegalArgumentException("Image is required")

            val response = api.createPlace(
                name = name,
                location = location,
                description = description!!,
                latitude = latitude!!,
                longitude = longitude!!,
                tagsSlugs = tagsSlugs!!,
                image = imagePart
            )

            if (response.isSuccess) {
                response.data?.toDomain() ?: throw Exception("Invalid response")
            } else {
                throw Exception(response.message ?: "Failed to create place")
            }
        }
    }

    override suspend fun updatePlace(place: Place): Result<Place> {
        // The API doesn't have an update endpoint yet
        // This is a placeholder implementation
        return safeApiCall {
            throw UnsupportedOperationException("Update place is not implemented yet")
        }
    }

    override suspend fun deletePlace(id: Long): Result<Unit> {
        // The API doesn't have a delete endpoint yet
        // This is a placeholder implementation
        return safeApiCall {
            throw UnsupportedOperationException("Delete place is not implemented yet")
        }
    }
}
