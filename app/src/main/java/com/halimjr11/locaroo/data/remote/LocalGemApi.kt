package com.halimjr11.locaroo.data.remote

import com.halimjr11.locaroo.data.remote.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface LocalGemApi {
    // Auth
    @POST("/auth/register")
    suspend fun register(@Body body: RegisterRequest): ApiResponse<AuthData>

    @POST("/auth/login")
    suspend fun login(@Body body: LoginRequest): ApiResponse<AuthData>

    @POST("/auth/refresh")
    suspend fun refresh(@Body body: RefreshRequest): ApiResponse<AuthTokens>

    // Places
    @GET("/places")
    suspend fun getPlaces(): ApiResponse<List<PlaceDto>>

    @GET("/places/{id}")
    suspend fun getPlace(@Path("id") id: Long): ApiResponse<PlaceDto>

    @GET("/places/{id}/reviews")
    suspend fun getPlaceReviews(@Path("id") id: Long): ApiResponse<List<ReviewDto>>

    // Create place (multipart)
    @Multipart
    @POST("/places")
    suspend fun createPlace(
        @Part("name") name: RequestBody,
        @Part("location") location: RequestBody,
        @Part("description") description: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        // API expects comma separated string for tagsSlugs per docs
        @Part("tagsSlugs") tagsSlugs: RequestBody,
        @Part image: MultipartBody.Part
    ): ApiResponse<PlaceCreateResponse>
}
