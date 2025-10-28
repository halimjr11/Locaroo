package com.halimjr11.locaroo.data.remote.model

import com.google.gson.annotations.SerializedName

data class AuthDataResponse(
    @SerializedName("access_token")
    val accessToken: String? = null,
    @SerializedName("refresh_token")
    val refreshToken: String? = null,
    @SerializedName("user")
    val user: UserResponse? = null
)