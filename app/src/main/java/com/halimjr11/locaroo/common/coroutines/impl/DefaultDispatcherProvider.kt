package com.halimjr11.locaroo.common.coroutines.impl

import com.halimjr11.locaroo.common.coroutines.CoroutinesDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DefaultDispatcherProvider @Inject constructor() : CoroutinesDispatcherProvider {
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val main: CoroutineDispatcher = Dispatchers.Main
    override val default: CoroutineDispatcher = Dispatchers.Default
}