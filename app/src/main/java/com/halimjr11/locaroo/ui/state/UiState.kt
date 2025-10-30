package com.halimjr11.locaroo.ui.state

sealed class UiState<out T> {
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
    object Loading : UiState<Nothing>()
}