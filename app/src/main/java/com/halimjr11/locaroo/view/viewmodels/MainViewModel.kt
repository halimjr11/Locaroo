package com.halimjr11.locaroo.view.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    // Main screen manages navigation between Home, Search, Journey.
    // TODO: Add user authentication state if needed.
}
