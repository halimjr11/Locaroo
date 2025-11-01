package com.halimjr11.locaroo.di

import com.halimjr11.locaroo.common.coroutines.CoroutinesDispatcherProvider
import com.halimjr11.locaroo.domain.repository.AuthLocalRepository
import com.halimjr11.locaroo.domain.repository.PlaceLocalRepository
import com.halimjr11.locaroo.domain.repository.PlaceRemoteRepository
import com.halimjr11.locaroo.domain.usecase.GetHomeDataUseCase
import com.halimjr11.locaroo.domain.usecase.GetJourneyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    /**
     * Provides a [GetHomeDataUseCase] instance using a [PlaceRemoteRepository] and a [CoroutinesDispatcherProvider].
     * @param repository The [PlaceRemoteRepository] to use.
     * @param dispatcher The [CoroutinesDispatcherProvider] to use.
     * @return A [GetHomeDataUseCase] instance.
     */
    @Provides
    @Singleton
    fun provideGetHomeDataUseCase(
        repository: PlaceRemoteRepository,
        authLocalRepository: AuthLocalRepository,
        dispatcher: CoroutinesDispatcherProvider
    ): GetHomeDataUseCase {
        return GetHomeDataUseCase(repository, authLocalRepository, dispatcher)
    }

    /**
     * Provides a [GetJourneyUseCase] instance using a [PlaceLocalRepository] and a [CoroutinesDispatcherProvider].
     * @param repository The [PlaceLocalRepository] to use.
     * @param dispatcher The [CoroutinesDispatcherProvider] to use.
     * @return A [GetJourneyUseCase] instance.
     */
    @Provides
    @Singleton
    fun provideGetJourneyUseCase(
        repository: PlaceLocalRepository,
        dispatcher: CoroutinesDispatcherProvider
    ): GetJourneyUseCase {
        return GetJourneyUseCase(repository, dispatcher)
    }
}
