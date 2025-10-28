package com.halimjr11.locaroo.data.remote

import com.halimjr11.locaroo.data.remote.model.AuthDataResponse
import com.halimjr11.locaroo.data.remote.model.AuthTokenResponse
import com.halimjr11.locaroo.data.remote.model.PlaceResponse
import com.halimjr11.locaroo.data.remote.model.ReviewResponse
import com.halimjr11.locaroo.data.remote.model.request.LoginRequest
import com.halimjr11.locaroo.data.remote.model.request.RefreshRequest
import com.halimjr11.locaroo.data.remote.model.request.RegisterRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface LocalGemApi {
    @POST("/auth/register")
    suspend fun register(@Body body: RegisterRequest): ApiResponse<AuthDataResponse>

    @POST("/auth/login")
    suspend fun login(@Body body: LoginRequest): ApiResponse<AuthDataResponse>

    @POST("/auth/refresh")
    suspend fun refresh(@Body body: RefreshRequest): ApiResponse<AuthTokenResponse>

    @GET("/places")
    suspend fun getPlaces(
        @Query("search") search: String? = null,
        @Query("tags") tags: List<String>? = null,
        @Query("offset") offset: Int? = null,
        @Query("limit") limit: Int? = null
    ): ApiResponse<List<PlaceResponse>>

    @GET("/places/{id}")
    suspend fun getPlace(@Path("id") id: Long): ApiResponse<PlaceResponse>

    @GET("/places/{id}/reviews")
    suspend fun getPlaceReviews(@Path("id") id: Long): ApiResponse<List<ReviewResponse>>

    @Multipart
    @POST("/places")
    suspend fun createPlace(
        @Part("name") name: RequestBody,
        @Part("location") location: RequestBody,
        @Part("description") description: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("tagsSlugs") tagsSlugs: RequestBody,
        @Part image: MultipartBody.Part
    ): ApiResponse<PlaceResponse>
}
