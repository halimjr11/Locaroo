package com.halimjr11.locaroo.ui.model

data class PlaceUi(
    val id: Long = 0,
    val name: String = "",
    val location: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val rating: Double = 0.0,
    val reviewsCount: Int = 0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)
