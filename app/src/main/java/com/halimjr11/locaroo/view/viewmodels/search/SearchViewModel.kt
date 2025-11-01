package com.halimjr11.locaroo.view.viewmodels.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halimjr11.locaroo.domain.repository.PlaceRemoteRepository
import com.halimjr11.locaroo.domain.utils.DomainResult
import com.halimjr11.locaroo.ui.mapper.toUi
import com.halimjr11.locaroo.ui.model.PlaceUi
import com.halimjr11.locaroo.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val placeRemoteRepository: PlaceRemoteRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _query = MutableStateFlow<String>("")
    val query: StateFlow<String> = _query.asStateFlow()

    val searchResults: StateFlow<UiState<List<PlaceUi>>> = _query
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf(UiState.Success(emptyList()))
            } else {
                val result = placeRemoteRepository.searchPlaces(query)
                val uiState = when (result) {
                    is DomainResult.Success -> {
                        UiState.Success(result.data.map { it.toUi() })
                    }

                    is DomainResult.Error -> {
                        UiState.Error(result.message)
                    }
                }
                flowOf(uiState)
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, UiState.Loading)


    /**
     * Updates the current query with a new query.
     * This function should be called whenever the user types a new query in the search bar.
     * The new query will be used to fetch new search results from the API.
     *
     * @param newQuery The new query to be used for searching.
     */
    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

}
