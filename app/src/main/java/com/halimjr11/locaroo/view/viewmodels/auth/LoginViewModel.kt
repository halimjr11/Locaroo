package com.halimjr11.locaroo.view.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halimjr11.locaroo.common.coroutines.CoroutinesDispatcherProvider
import com.halimjr11.locaroo.domain.repository.AuthRemoteRepository
import com.halimjr11.locaroo.domain.utils.DomainResult
import com.halimjr11.locaroo.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRemoteRepository,
    private val dispatcher: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _loginState: MutableStateFlow<UiState<Unit>> = MutableStateFlow(UiState.Idle)
    val loginState: StateFlow<UiState<Unit>> = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        _loginState.value = UiState.Loading
        viewModelScope.launch(dispatcher.io) {
            val result = authRepository.login(email, password)
            when (result) {
                is DomainResult.Success -> {
                    _loginState.value = UiState.Success(Unit)
                }

                is DomainResult.Error -> {
                    _loginState.value = UiState.Error(result.message)
                }
            }
        }
    }
}
