package com.halimjr11.locaroo.di

import com.halimjr11.locaroo.common.AuthEventManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthEventManager(): AuthEventManager {
        return AuthEventManager()
    }
}