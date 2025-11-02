package com.halimjr11.locaroo.common

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class AuthEventManager @Inject constructor() {
    private val _authEvents = MutableSharedFlow<AuthEvent>()
    val authEvents = _authEvents.asSharedFlow()

    suspend fun onUnauthorized() {
        _authEvents.emit(AuthEvent.Unauthorized)
    }
}