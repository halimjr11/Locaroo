package com.halimjr11.locaroo.data.remote.interceptor

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.halimjr11.locaroo.data.utils.AuthPrefKeys
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val store: DataStore<Preferences>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val modifiedRequest = when (request.url.encodedPath) {
            "/login", "/register", "/refresh" -> {
                request
                    .newBuilder()
                    .build()
            }

            else -> {
                val accessToken = runBlocking {
                    store.data.map { it[AuthPrefKeys.ACCESS_TOKEN] }.first()
                }
                request
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $accessToken")
                    .build()
            }
        }
        return chain.proceed(modifiedRequest)
    }
}