package com.halimjr11.locaroo.common

sealed class AuthEvent {
    object Unauthorized : AuthEvent()
}