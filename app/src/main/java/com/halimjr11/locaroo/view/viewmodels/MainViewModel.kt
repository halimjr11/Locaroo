package com.halimjr11.locaroo.view.viewmodels

import androidx.lifecycle.ViewModel
import com.halimjr11.locaroo.common.AuthEventManager
import com.halimjr11.locaroo.domain.repository.AuthLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authLocalRepository: AuthLocalRepository,
    private val authEventManager: AuthEventManager
) : ViewModel() {
    val authEvents = authEventManager.authEvents
    val isLoggedIn: Flow<Boolean?> = authLocalRepository.isLoggedIn()
}
