package com.halimjr11.locaroo.view.viewmodels.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halimjr11.locaroo.common.coroutines.CoroutinesDispatcherProvider
import com.halimjr11.locaroo.domain.usecase.GetHomeDataUseCase
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
class HomeViewModel @Inject constructor(
    private val getHomeDataUseCase: GetHomeDataUseCase,
    private val dispatcher: CoroutinesDispatcherProvider,
    private val uiDataMapper: UiDataMapper
) : ViewModel() {

    private val _places = MutableStateFlow<UiState<Pair<List<PlaceUi>, String>>>(UiState.Loading)
    val places: StateFlow<UiState<Pair<List<PlaceUi>, String>>> = _places.asStateFlow()


    init {
        loadPlaces()
    }

    /**
     * Load places data
     *
     * This function loads the places data from the API and updates the
     * [_places] state with the result. If the result is a success, it
     * maps the data to the UI model and wraps it in a [UiState.Success]
     * object. If the result is an error, it wraps the error message in a
     * [UiState.Error] object.
     */
    fun loadPlaces() = viewModelScope.launch(dispatcher.io) {
        val result = getHomeDataUseCase()
        _places.value = when (result) {
            is DomainResult.Success -> {
                UiState.Success(
                    Pair(
                        result.data.first.map { uiDataMapper.mapPlaceToUI(it) },
                        result.data.second
                    )
                )
            }

            is DomainResult.Error -> {
                UiState.Error(result.message)
            }
        }
    }
}
