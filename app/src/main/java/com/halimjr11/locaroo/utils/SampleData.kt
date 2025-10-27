package com.halimjr11.locaroo.utils

import com.halimjr11.locaroo.model.Destination

object SampleData {
    val destinations: List<Destination> = listOf(
        Destination(
            id = "1",
            name = "Niladri Reservoir",
            imageUrl = null,
            location = "Jakarta",
            rating = 4.7
        ),
        Destination(
            id = "2",
            name = "Derma View",
            imageUrl = null,
            location = "Bandung",
            rating = 4.5
        ),
        Destination(
            id = "3",
            name = "Bromo Mountain",
            imageUrl = null,
            location = "Malang",
            rating = 4.8
        ),
    )
}
