package com.halimjr11.locaroo.view.viewmodels.splash

import androidx.lifecycle.ViewModel
import com.halimjr11.locaroo.domain.repository.AuthLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authLocalRepository: AuthLocalRepository
) : ViewModel() {

}
