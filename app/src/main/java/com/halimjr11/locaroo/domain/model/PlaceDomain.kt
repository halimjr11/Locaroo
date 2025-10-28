package com.halimjr11.locaroo.domain.model

data class PlaceDomain(
    val id: Long = 0L,
    val name: String = "",
    val location: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val tagsSlugs: List<String> = emptyList(),
    val userId: Long = 0L,
    val avgRating: Double = 0.0,
    val reviewsCount: Int = 0,
    val createdAt: String = "",
    val updatedAt: String = ""
)
