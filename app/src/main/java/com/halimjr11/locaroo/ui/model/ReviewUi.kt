package com.halimjr11.locaroo.ui.model

data class ReviewUi(
    val id: Long,
    val rating: Int,
    val comment: String,
    val user: AuthUserUi,
    val createdAt: String
)
