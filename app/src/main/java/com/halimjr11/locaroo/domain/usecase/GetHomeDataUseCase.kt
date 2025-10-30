package com.halimjr11.locaroo.domain.usecase

import com.halimjr11.locaroo.common.coroutines.CoroutinesDispatcherProvider
import com.halimjr11.locaroo.domain.model.PlaceDomain
import com.halimjr11.locaroo.domain.repository.AuthLocalRepository
import com.halimjr11.locaroo.domain.repository.PlaceRepository
import com.halimjr11.locaroo.domain.utils.DomainResult
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetHomeDataUseCase @Inject constructor(
    private val placeRepository: PlaceRepository,
    private val authLocalRepository: AuthLocalRepository,
    private val dispatcher: CoroutinesDispatcherProvider
) {
    suspend operator fun invoke(): DomainResult<Pair<List<PlaceDomain>, String>> =
        withContext(dispatcher.io)
        {
            val result = placeRepository.getPlaces()
            val name = authLocalRepository.getUserName().orEmpty()
            return@withContext when (result) {
                is DomainResult.Success -> {
                    DomainResult.Success(Pair(result.data, name))
                }

                is DomainResult.Error -> {
                    DomainResult.Error(result.message)
                }
            }
        }
}