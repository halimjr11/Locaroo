package com.halimjr11.locaroo.common.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutinesDispatcherProvider {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
}