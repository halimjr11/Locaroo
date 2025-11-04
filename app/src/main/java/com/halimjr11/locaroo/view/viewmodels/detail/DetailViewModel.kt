package com.halimjr11.locaroo.view.viewmodels.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halimjr11.locaroo.common.coroutines.CoroutinesDispatcherProvider
import com.halimjr11.locaroo.common.orLongZero
import com.halimjr11.locaroo.domain.model.PlaceDomain
import com.halimjr11.locaroo.domain.repository.PlaceLocalRepository
import com.halimjr11.locaroo.domain.repository.PlaceRemoteRepository
import com.halimjr11.locaroo.domain.utils.DomainResult
import com.halimjr11.locaroo.ui.mapper.UiDataMapper
import com.halimjr11.locaroo.ui.model.PlaceUi
import com.halimjr11.locaroo.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val placeRemoteRepository: PlaceRemoteRepository,
    private val placeLocalRepository: PlaceLocalRepository,
    private val dispatcher: CoroutinesDispatcherProvider,
    private val uiDataMapper: UiDataMapper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val placeId: Long? = savedStateHandle.get<Long>("placeId")
    private var placeDomain = PlaceDomain()

    private val _detailState = MutableStateFlow<UiState<PlaceUi>>(UiState.Loading)
    val detailState: StateFlow<UiState<PlaceUi>> = _detailState

    private val _isFavorite: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    init {
        placeId?.let { loadPlace(it) }
    }

    /**
     * Load a place by id.
     *
     * @param id The id of the place.
     */
    private fun loadPlace(id: Long) = viewModelScope.launch(dispatcher.io) {
        val result = placeRemoteRepository.getPlaceById(id)
        checkFavorite()
        _detailState.value = when (result) {
            is DomainResult.Success -> {
                placeDomain = result.data
                UiState.Success(uiDataMapper.mapPlaceToUI(result.data))
            }

            is DomainResult.Error -> {
                UiState.Error(result.message)
            }
        }

    }

    fun checkFavorite() = viewModelScope.launch(dispatcher.io) {
        _isFavorite.value = placeLocalRepository.isFavorite(placeId.orLongZero())
    }

    fun toggleFavorite() = viewModelScope.launch() {
        if (_isFavorite.value) {
            placeLocalRepository.deleteFavorite(placeId.orLongZero())
        } else {
            placeLocalRepository.insertFavorite(placeDomain)
        }
        _isFavorite.value = !isFavorite.value
    }

    /**
     * Retry loading the place with the given id.
     */
    fun retry() {
        placeId?.let { loadPlace(it) }
    }
}
