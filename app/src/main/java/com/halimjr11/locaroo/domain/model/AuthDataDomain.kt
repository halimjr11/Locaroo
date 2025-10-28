package com.halimjr11.locaroo.domain.model

data class AuthDataDomain(
    val accessToken: String = "",
    val refreshToken: String = "",
    val user: AuthUserDomain = AuthUserDomain()
)
