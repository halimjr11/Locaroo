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
class LoginViewModel @Inject constructor(
    private val dispatcher: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _loginState: MutableStateFlow<UiState<Unit>> = MutableStateFlow(UiState.Loading)
    val loginState: StateFlow<UiState<Unit>> = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        _loginState.value = UiState.Loading
        viewModelScope.launch(dispatcher.io) {
            // TODO: Replace with real auth call
            if (email.isNotBlank() && password.isNotBlank()) {
                _loginState.value = UiState.Success(Unit)
            } else {
                _loginState.value = UiState.Error("Email and password must not be empty")
            }
        }
    }

    fun reset() {
        _loginState.value = UiState.Loading
    }
}
