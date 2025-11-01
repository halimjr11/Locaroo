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
    /**
     * Register a new user to the platform.
     * @param body The register request body.
     * @return An ApiResponse containing the registered user's data.
     */
    @POST("auth/register")
    suspend fun register(@Body body: RegisterRequest): ApiResponse<AuthDataResponse>

    /**
     * Log in to the platform.
     * @param body The login request body containing the email and password.
     * @return An ApiResponse containing the logged in user's data.
     */
    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest): ApiResponse<AuthDataResponse>

    /**
     * Refresh the user's token.
     * @param body The refresh request body containing the refresh token.
     * @return An ApiResponse containing the refreshed user's token.
     */
    @POST("auth/refresh")
    suspend fun refresh(@Body body: RefreshRequest): ApiResponse<AuthTokenResponse>

    /**
     * Retrieves a list of places from the server.
     * @param search Optional search query to filter places by name or description.
     * @param tags Optional list of tags to filter places by.
     * @param offset Optional offset to start retrieving places from. Defaults to 0.
     * @param limit Optional limit to the number of places to retrieve. Defaults to 20.
     * @return An ApiResponse containing a list of PlaceResponse objects.
     */
    @GET("places")
    suspend fun getPlaces(
        @Query("search") search: String? = null,
        @Query("tags") tags: List<String>? = null,
        @Query("offset") offset: Int? = null,
        @Query("limit") limit: Int? = null
    ): ApiResponse<List<PlaceResponse>>

    /**
     * Retrieves a single place from the server by its ID.
     * @param id The ID of the place to retrieve.
     * @return An ApiResponse containing a PlaceResponse object.
     */
    @GET("places/{id}")
    suspend fun getPlace(@Path("id") id: Long): ApiResponse<PlaceResponse>

    /**
     * Retrieves a list of reviews for a place from the server.
     * @param id The ID of the place to retrieve reviews for.
     * @return An ApiResponse containing a list of ReviewResponse objects.
     */
    @GET("places/{id}/reviews")
    suspend fun getPlaceReviews(@Path("id") id: Long): ApiResponse<List<ReviewResponse>>

    /**
     * Creates a new place on the server.
     * @param name The name of the place.
     * @param location The location of the place.
     * @param description The description of the place.
     * @param latitude The latitude of the place.
     * @param longitude The longitude of the place.
     * @param tagsSlugs The tags of the place separated by commas.
     * @param image The image of the place.
     * @return An ApiResponse containing a PlaceResponse object if successful, or an ErrorResponse object if not.
     */
    @Multipart
    @POST("places")
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
