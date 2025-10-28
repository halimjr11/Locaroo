package com.halimjr11.locaroo.data.remote.model

import com.google.gson.annotations.SerializedName

data class AuthTokens(
    @SerializedName("access_token")
    val accessToken: String? = null,
    @SerializedName("refresh_token")
    val refreshToken: String? = null
)