package com.halimjr11.locaroo.data.remote.model

import com.google.gson.annotations.SerializedName

data class ReviewDto(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("rating")
    val rating: Int? = null,
    @SerializedName("comment")
    val comment: String? = null,
    @SerializedName("user")
    val user: ReviewUserDto? = null,
    @SerializedName("place_id")
    val placeId: Long? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null
)