package com.halimjr11.locaroo.data.remote.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("location")
    val location: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("imageUrl")
    val imageUrl: String? = null,
    @SerializedName("latitude")
    val latitude: Double? = null,
    @SerializedName("longitude")
    val longitude: Double? = null,
    @SerializedName("tagsSlugs")
    val tagsSlugs: List<String>? = null,
    @SerializedName("userId")
    val userId: Long? = null,
    @SerializedName("avgRating")
    val avgRating: Double? = null,
    @SerializedName("reviewsCount")
    val reviewsCount: Int? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null
)
