package com.halimjr11.locaroo.di

import com.halimjr11.locaroo.data.repository.AuthLocalRepositoryImpl
import com.halimjr11.locaroo.data.repository.AuthRemoteRepositoryImpl
import com.halimjr11.locaroo.data.repository.PlaceLocalRepositoryImpl
import com.halimjr11.locaroo.data.repository.PlaceRemoteRepositoryImpl
import com.halimjr11.locaroo.domain.repository.AuthLocalRepository
import com.halimjr11.locaroo.domain.repository.AuthRemoteRepository
import com.halimjr11.locaroo.domain.repository.PlaceLocalRepository
import com.halimjr11.locaroo.domain.repository.PlaceRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    /**
     * Provides an implementation of the {@link AuthLocalRepository} interface.
     * @param impl The implementation of the {@link AuthLocalRepository} interface.
     * @return An instance of the {@link AuthLocalRepository} interface.
     */
    @Binds
    abstract fun provideAuthLocalRepository(impl: AuthLocalRepositoryImpl): AuthLocalRepository

    /**
     * Provides an implementation of the {@link AuthRemoteRepository} interface.
     * @param impl The implementation of the {@link AuthRemoteRepository} interface.
     * @return An instance of the {@link AuthRemoteRepository} interface.
     */
    @Binds
    abstract fun provideAuthRemoteRepository(impl: AuthRemoteRepositoryImpl): AuthRemoteRepository

    /**
     * Provides an implementation of the {@link PlaceLocalRepository} interface.
     * @param impl The implementation of the {@link PlaceLocalRepository} interface.
     * @return An instance of the {@link PlaceLocalRepository} interface.
     * @see PlaceLocalRepositoryImpl for the implementation details.
     */
    @Binds
    abstract fun providePlaceLocalRepository(impl: PlaceLocalRepositoryImpl): PlaceLocalRepository

    /**
     * Provides an implementation of the {@link PlaceRemoteRepository} interface.
     * @param impl The implementation of the {@link PlaceRemoteRepository} interface.
     * @return An instance of the {@link PlaceRemoteRepository} interface.
     * @see PlaceRemoteRepositoryImpl for the implementation details.
     */
    @Binds
    abstract fun providePlaceRemoteRepository(impl: PlaceRemoteRepositoryImpl): PlaceRemoteRepository
}