package com.halimjr11.locaroo.view.viewmodels.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halimjr11.locaroo.domain.repository.PlaceRepository
import com.halimjr11.locaroo.domain.utils.DomainResult
import com.halimjr11.locaroo.ui.mapper.toUi
import com.halimjr11.locaroo.ui.model.PlaceUi
import com.halimjr11.locaroo.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val placeRepository: PlaceRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val placeId: Long? = savedStateHandle.get<Long>("placeId")

    private val _detailState = MutableStateFlow<UiState<PlaceUi>>(UiState.Loading)
    val detailState: StateFlow<UiState<PlaceUi>> = _detailState

    init {
        placeId?.let { loadPlace(it) }
    }

    /**
     * Load a place by id.
     *
     * @param id The id of the place.
     */
    private fun loadPlace(id: Long) = viewModelScope.launch {
        val result = placeRepository.getPlaceById(id)
        _detailState.value = when (result) {
            is DomainResult.Success -> {
                UiState.Success(result.data.toUi())
            }

            is DomainResult.Error -> {
                UiState.Error(result.message)
            }
        }

    }

    fun retry() {
        placeId?.let { loadPlace(it) }
    }
}
