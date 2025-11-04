package com.halimjr11.locaroo.view.viewmodels.journey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halimjr11.locaroo.common.DateManager
import com.halimjr11.locaroo.common.coroutines.CoroutinesDispatcherProvider
import com.halimjr11.locaroo.domain.repository.PlaceLocalRepository
import com.halimjr11.locaroo.ui.mapper.UiDataMapper
import com.halimjr11.locaroo.ui.model.ScheduleUi
import com.halimjr11.locaroo.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class JourneyViewModel @Inject constructor(
    private val dateManager: DateManager,
    private val placeLocalRepository: PlaceLocalRepository,
    private val uiDataMapper: UiDataMapper,
    private val dispatcher: CoroutinesDispatcherProvider
) : ViewModel() {
    var dateSelected: LocalDate = dateManager.today()
        private set
    private val _journeySchedule: MutableStateFlow<UiState<List<ScheduleUi>>> =
        MutableStateFlow(UiState.Loading)
    val journeySchedule: StateFlow<UiState<List<ScheduleUi>>> = _journeySchedule.asStateFlow()

    private val _startOfWeek: MutableStateFlow<LocalDate> =
        MutableStateFlow(dateManager.startOfWeek())
    val startOfWeek: StateFlow<LocalDate> = _startOfWeek.asStateFlow()

    private val _daysOfWeek: MutableStateFlow<List<LocalDate>> =
        MutableStateFlow(dateManager.daysOfWeek())
    val daysOfWeek: StateFlow<List<LocalDate>> = _daysOfWeek.asStateFlow()

    init {
        loadJourneySchedule()
    }

    /**
     * Loads the journey schedule data for a given date.
     * @param date The date to search for, in the format "yyyy-MM-dd".
     * @return A list of all places in the database that match the given date.
     */
    fun loadJourneySchedule() = viewModelScope.launch(dispatcher.io) {
        val result = placeLocalRepository.getPlaces(dateSelected.toString())
        _journeySchedule.value = if (result.isNotEmpty()) {
            UiState.Success(result.map { uiDataMapper.mapScheduleToUI(it) })
        } else {
            UiState.Error("No data found")
        }
    }

    fun updateStartOfWeek(date: LocalDate) = viewModelScope.launch(dispatcher.io) {
        _startOfWeek.update {
            dateManager.startOfWeek(date)
        }
        _daysOfWeek.update {
            dateManager.daysOfWeek(date)
        }
    }

    fun updateDateSelected(date: LocalDate) {
        dateSelected = date
        loadJourneySchedule()
    }
}

