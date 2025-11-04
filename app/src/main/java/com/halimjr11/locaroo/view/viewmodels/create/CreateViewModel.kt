package com.halimjr11.locaroo.view.viewmodels.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halimjr11.locaroo.common.coroutines.CoroutinesDispatcherProvider
import com.halimjr11.locaroo.domain.repository.PlaceRemoteRepository
import com.halimjr11.locaroo.domain.utils.DomainResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val placeRemoteRepository: PlaceRemoteRepository,
    private val dispatcher: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _status = MutableStateFlow<String?>(null)
    val status: StateFlow<String?> = _status.asStateFlow()

    val statusText: String?
        get() = _status.value

    fun createPlace(
        name: String,
        description: String,
        latitude: Double,
        longitude: Double,
        location: String,
        tagsSlugs: List<String>,
        imageAbsolutePath: String
    ) {
        viewModelScope.launch(dispatcher.io) {
            _status.value = "Uploading..."
            when (val res = placeRemoteRepository.addPlace(
                name = name,
                description = description,
                latitude = latitude,
                longitude = longitude,
                location = location,
                tagsSlugs = tagsSlugs,
                imageUri = imageAbsolutePath
            )) {
                is DomainResult.Success -> _status.value = "Success: ${res.data.name}"
                is DomainResult.Error -> _status.value = "Error: ${res.message}"
            }
        }
    }
}
