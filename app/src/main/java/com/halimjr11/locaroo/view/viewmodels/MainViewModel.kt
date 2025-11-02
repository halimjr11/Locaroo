package com.halimjr11.locaroo.view.viewmodels

import androidx.lifecycle.ViewModel
import com.halimjr11.locaroo.common.AuthEventManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authEventManager: AuthEventManager
) : ViewModel() {
    val authEvents = authEventManager.authEvents
}
