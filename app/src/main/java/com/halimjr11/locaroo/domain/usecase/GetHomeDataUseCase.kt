package com.halimjr11.locaroo.domain.usecase

import com.halimjr11.locaroo.common.coroutines.CoroutinesDispatcherProvider
import com.halimjr11.locaroo.domain.model.PlaceDomain
import com.halimjr11.locaroo.domain.repository.AuthLocalRepository
import com.halimjr11.locaroo.domain.repository.PlaceRemoteRepository
import com.halimjr11.locaroo.domain.utils.DomainResult
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetHomeDataUseCase @Inject constructor(
    private val placeRemoteRepository: PlaceRemoteRepository,
    private val authLocalRepository: AuthLocalRepository,
    private val dispatcher: CoroutinesDispatcherProvider
) {
    suspend operator fun invoke(
        city: String
    ): DomainResult<Triple<List<PlaceDomain>, List<PlaceDomain>, String>> =
        withContext(dispatcher.io) {
            val cityDeferred = async { placeRemoteRepository.getPlaces(city) }
            val allDeferred = async { placeRemoteRepository.getPlaces() }
            val name = authLocalRepository.getUserName().orEmpty()

            val cityResult = cityDeferred.await()
            val allResult = allDeferred.await()

            val cityData = (cityResult as? DomainResult.Success)?.data
            val allData = (allResult as? DomainResult.Success)?.data

            if (cityData == null && allData == null) {
                val errorMessage = (allResult as? DomainResult.Error)?.message ?: "Unknown error"
                return@withContext DomainResult.Error(errorMessage)
            }

            DomainResult.Success(
                Triple(
                    cityData.orEmpty(),
                    allData.orEmpty(),
                    name
                )
            )
        }
}