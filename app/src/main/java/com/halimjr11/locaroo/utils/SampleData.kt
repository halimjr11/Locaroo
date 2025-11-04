package com.halimjr11.locaroo.utils

import com.halimjr11.locaroo.ui.model.PlaceUi

object SampleData {
    val destinations: List<PlaceUi> = listOf(
        PlaceUi(
            id = 1,
            name = "Niladri Reservoir",
            location = "Jakarta",
            description = "A beautiful reservoir",
            imageUrl = "",
            rating = 4.7,
            reviewsCount = 10,
            latitude = 0.0,
            longitude = 0.0
        ),
        PlaceUi(
            id = 2,
            name = "Derma View",
            location = "Bandung",
            description = "Scenic view",
            imageUrl = "",
            rating = 4.5,
            reviewsCount = 8,
            latitude = 0.0,
            longitude = 0.0
        ),
        PlaceUi(
            id = 3,
            name = "Bromo Mountain",
            location = "Malang",
            description = "Volcanic mountain",
            imageUrl = "",
            rating = 4.8,
            reviewsCount = 15,
            latitude = 0.0,
            longitude = 0.0
        ),
    )
}
