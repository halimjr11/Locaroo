package com.halimjr11.locaroo.domain.model

data class ScheduleItem(
    val id: Long = 0,
    val date: String = "",
    val title: String = "",
    val location: String = ""
)