package com.halimjr11.locaroo.view.viewmodels.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halimjr11.locaroo.domain.repository.PlaceLocalRepository
import com.halimjr11.locaroo.ui.mapper.UiDataMapper
import com.halimjr11.locaroo.ui.model.PlaceUi
import com.halimjr11.locaroo.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: PlaceLocalRepository,
    private val uiDataMapper: UiDataMapper
) : ViewModel() {

    private val _favorites = MutableStateFlow<UiState<List<PlaceUi>>>(UiState.Loading)
    val favorites: StateFlow<UiState<List<PlaceUi>>> = runBlocking {
        repository.getFavorites()
            .map { list ->
                if (list.isEmpty()) {
                    UiState.Error("Belum ada tempat favorit")
                } else {
                    UiState.Success(list.map { uiDataMapper.mapPlaceToUI(it) })
                }
            }
            .catch { e -> _favorites.value = UiState.Error(e.message ?: "Unknown error") }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = UiState.Loading
            )
    }
}
