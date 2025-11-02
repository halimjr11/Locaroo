package com.halimjr11.locaroo.view.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halimjr11.locaroo.common.coroutines.CoroutinesDispatcherProvider
import com.halimjr11.locaroo.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val dispatcher: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _registerState: MutableStateFlow<UiState<Unit>> = MutableStateFlow(UiState.Loading)
    val registerState: StateFlow<UiState<Unit>> = _registerState.asStateFlow()

    fun register(name: String, email: String, password: String) {
        _registerState.value = UiState.Loading
        viewModelScope.launch(dispatcher.io) {
            // TODO: Replace with real registration call
            if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                _registerState.value = UiState.Success(Unit)
            } else {
                _registerState.value = UiState.Error("All fields are required")
            }
        }
    }

    fun reset() {
        _registerState.value = UiState.Loading
    }
}
