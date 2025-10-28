package com.halimjr11.locaroo.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("email")
    val email: String? = null
)