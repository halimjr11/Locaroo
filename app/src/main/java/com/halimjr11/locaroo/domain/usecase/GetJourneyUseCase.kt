package com.halimjr11.locaroo.domain.usecase

import com.halimjr11.locaroo.common.coroutines.CoroutinesDispatcherProvider
import com.halimjr11.locaroo.domain.model.ScheduleDomain
import com.halimjr11.locaroo.domain.repository.PlaceLocalRepository
import com.halimjr11.locaroo.domain.utils.DomainResult
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetJourneyUseCase @Inject constructor(
    private val repository: PlaceLocalRepository,
    private val dispatcher: CoroutinesDispatcherProvider
) {
    suspend operator fun invoke(date: String): DomainResult<List<ScheduleDomain>> =
        withContext(dispatcher.io) {
            val result = repository.getPlaces(date)
            return@withContext if (result.isNotEmpty()) {
                DomainResult.Success(result)
            } else {
                DomainResult.Error("No data found")
            }
        }
}