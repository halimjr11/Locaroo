package com.halimjr11.locaroo.data.remote

data class ApiResponse<T>(
    val status: String,
    val message: String,
    val data: T?
)
