package com.halimjr11.locaroo.data.repository

import com.halimjr11.locaroo.data.mapper.RemoteDataMapper
import com.halimjr11.locaroo.data.remote.LocalGemApi
import com.halimjr11.locaroo.domain.utils.DomainResult
import com.halimjr11.locaroo.domain.model.PlaceDomain
import com.halimjr11.locaroo.domain.repository.PlaceRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val api: LocalGemApi,
    private val mapper: RemoteDataMapper
) : PlaceRepository, BaseRepository() {

    override suspend fun getPlaces(page: Int, pageSize: Int): DomainResult<List<PlaceDomain>> {
        return safeApiCall {
            api.getPlaces().data?.map { mapper.mapPlaceToDomain(it) } ?: emptyList()
        }
    }

    override suspend fun getPlaceById(id: Long): DomainResult<PlaceDomain> {
        return safeApiCall {
            val response = api.getPlace(id)
            api.getPlace(id).data?.let { mapper.mapPlaceToDomain(it) } ?: PlaceDomain()
        }
    }

    override suspend fun searchPlaces(
        query: String,
        page: Int,
        pageSize: Int
    ): DomainResult<List<PlaceDomain>> = safeApiCall {
        api.getPlaces().data?.map { mapper.mapPlaceToDomain(it) }.orEmpty()
    }

    override suspend fun addPlace(
        name: String,
        description: String,
        latitude: Double,
        longitude: Double,
        location: String,
        tagsSlugs: List<String>,
        imageUri: String
    ): DomainResult<PlaceDomain> {
        return safeApiCall {
            val imageFile = File(imageUri)
            val imageRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
            val imagePart =
                MultipartBody.Part.createFormData("image", imageFile.name, imageRequestBody)
            api.createPlace(
                name.toRequestBody("text/plain".toMediaType()),
                location.toRequestBody("text/plain".toMediaType()),
                description.toRequestBody("text/plain".toMediaType()),
                latitude.toString().toRequestBody("text/plain".toMediaType()),
                longitude.toString().toRequestBody("text/plain".toMediaType()),
                tagsSlugs.joinToString(",").toRequestBody("text/plain".toMediaType()),
                imagePart
            ).data?.let { mapper.mapPlaceToDomain(it) } ?: PlaceDomain()
        }
    }
}
