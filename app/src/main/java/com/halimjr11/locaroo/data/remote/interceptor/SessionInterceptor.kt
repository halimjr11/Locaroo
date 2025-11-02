package com.halimjr11.locaroo.data.remote.interceptor

import com.halimjr11.locaroo.common.AuthEventManager
import com.halimjr11.locaroo.domain.repository.AuthLocalRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class SessionInterceptor @Inject constructor(
    private val localRepository: AuthLocalRepository,
    private val authEventManager: AuthEventManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = chain.proceed(request)

        runBlocking {
            if (response.code == 401) {
                localRepository.clearAuthData()
                authEventManager.onUnauthorized()
            }
        }

        return response
    }
}