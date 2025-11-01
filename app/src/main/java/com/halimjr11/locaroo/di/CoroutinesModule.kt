package com.halimjr11.locaroo.di

import com.halimjr11.locaroo.common.coroutines.CoroutinesDispatcherProvider
import com.halimjr11.locaroo.common.coroutines.impl.DefaultDispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CoroutinesModule {
    /**
     * Binds the [DefaultDispatcherProvider] to the [CoroutinesDispatcherProvider]
     * @return A [CoroutinesDispatcherProvider] instance
     */
    @Binds
    abstract fun provideCoroutineProvider(impl: DefaultDispatcherProvider): CoroutinesDispatcherProvider
}