package com.halimjr11.locaroo.data.remote.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class RegisterRequest(
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("email")
    val email: String? = null
)

@Serializable
data class LoginRequest(
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("password")
    val password: String? = null
)

@Serializable
data class RefreshRequest(
    @SerializedName("refresh_token")
    val refreshToken: String? = null
)

// Responses

@Serializable
data class AuthTokens(
    @SerializedName("access_token")
    val accessToken: String? = null,
    @SerializedName("refresh_token")
    val refreshToken: String? = null
)

@Serializable
data class UserDto(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("name")
    val name: String? = null
)

data class AuthData(
    @SerializedName("access_token")
    val accessToken: String? = null,
    @SerializedName("refresh_token")
    val refreshToken: String? = null,
    @SerializedName("user")
    val user: UserDto? = null
)
