package com.halimjr11.locaroo.domain.model

data class AuthUser(
    val id: Long,
    val username: String? = null,
    val email: String,
    val name: String? = null
)
