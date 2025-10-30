package com.halimjr11.locaroo.ui.model

data class PlaceUi(
    val id: String,
    val name: String,
    val location: String,
    val description: String,
    val imageUrl: String?,
    val rating: Double,
    val reviewsCount: Int,
    val latitude: Double,
    val longitude: Double
)
