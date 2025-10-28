package com.halimjr11.locaroo.domain.model

data class AuthUserDomain(
    val id: Long = 0,
    val username: String = "",
    val email: String = "",
    val name: String = ""
)
