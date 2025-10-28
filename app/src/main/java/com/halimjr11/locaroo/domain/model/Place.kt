package com.halimjr11.locaroo.domain.model

data class Place(
    val id: Long? = null,
    val name: String? = null,
    val location: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val tagsSlugs: List<String>? = null,
    val userId: Long? = null,
    val avgRating: Double? = null,
    val reviewsCount: Int? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

data class Review(
    val id: Long? = null,
    val rating: Int? = null,
    val comment: String? = null,
    val user: ReviewUser? = null,
    val placeId: Long? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

data class ReviewUser(
    val id: Long? = null,
    val name: String? = null,
    val email: String? = null
)

data class AuthData(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val user: AuthUser? = null
)

// Request models
data class LoginRequest(
    val email: String? = null,
    val password: String? = null
)

data class RegisterRequest(
    val username: String? = null,
    val password: String? = null,
    val email: String? = null
)

data class RefreshRequest(
    val refreshToken: String? = null
)
