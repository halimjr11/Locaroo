package com.halimjr11.locaroo.ui.model

data class AuthDataUi(
    val accessToken: String,
    val refreshToken: String,
    val user: AuthUserUi
)
