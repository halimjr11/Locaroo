package com.halimjr11.locaroo.domain.model

data class ReviewDomain(
    val id: Long = 0L,
    val rating: Int = 0,
    val comment: String = "",
    val user: AuthUserDomain = AuthUserDomain(),
    val placeId: Long = 0L,
    val createdAt: String = "",
    val updatedAt: String = ""
)
