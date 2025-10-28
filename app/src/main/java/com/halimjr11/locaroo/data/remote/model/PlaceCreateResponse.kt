package com.halimjr11.locaroo.data.remote.model

import com.google.gson.annotations.SerializedName

data class PlaceCreateResponse(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("location")
    val location: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("image_url")
    val imageUrl: String? = null,
    @SerializedName("latitude")
    val latitude: Double? = null,
    @SerializedName("longitude")
    val longitude: Double? = null,
    @SerializedName("tags_slugs")
    val tagsSlugs: List<String>? = null,
    @SerializedName("user_id")
    val userId: Long? = null,
    @SerializedName("avg_rating")
    val avgRating: Double? = null,
    @SerializedName("reviews_count")
    val reviewsCount: Int? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null
)