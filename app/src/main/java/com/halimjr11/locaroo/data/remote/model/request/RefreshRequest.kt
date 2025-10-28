package com.halimjr11.locaroo.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class RefreshRequest(
    @SerializedName("refresh_token")
    val refreshToken: String? = null
)