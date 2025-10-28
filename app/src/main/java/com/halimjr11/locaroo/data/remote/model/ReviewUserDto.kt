package com.halimjr11.locaroo.data.remote.model

import com.google.gson.annotations.SerializedName

data class ReviewUserDto(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("email")
    val email: String? = null
)