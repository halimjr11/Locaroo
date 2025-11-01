package com.halimjr11.locaroo.di

import com.halimjr11.locaroo.data.mapper.LocalDataMapper
import com.halimjr11.locaroo.data.mapper.RemoteDataMapper
import com.halimjr11.locaroo.data.mapper.impl.LocalDataMapperImpl
import com.halimjr11.locaroo.data.mapper.impl.RemoteDataMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {
    /**
     * Provides the [LocalDataMapper] that is responsible for mapping local data models to domain models.
     * @param impl the implementation of [LocalDataMapper] to provide.
     * @return an instance of [LocalDataMapper] that is responsible for mapping local data models to domain models.
     */
    @Binds
    abstract fun provideLocalDataMapper(impl: LocalDataMapperImpl): LocalDataMapper

    /**
     * Provides the [RemoteDataMapper] that is responsible for mapping remote data models to domain models.
     * @param impl the implementation of [RemoteDataMapper] to provide.
     * @return an instance of [RemoteDataMapper] that is responsible for mapping remote data models to domain models.
     */
    @Binds
    abstract fun provideRemoteDataMapper(impl: RemoteDataMapperImpl): RemoteDataMapper
}