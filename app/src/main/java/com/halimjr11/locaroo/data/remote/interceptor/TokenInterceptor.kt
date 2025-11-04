package com.halimjr11.locaroo.data.remote.interceptor

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.halimjr11.locaroo.data.remote.LocalGemApi
import com.halimjr11.locaroo.data.remote.model.AuthTokenResponse
import com.halimjr11.locaroo.data.remote.model.request.RefreshRequest
import com.halimjr11.locaroo.data.utils.AuthPrefKeys
import com.halimjr11.locaroo.utils.Constant
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val chuckInterceptor: ChuckerInterceptor
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = runBlocking {
            dataStore.data.map { it[AuthPrefKeys.REFRESH_TOKEN] }.first()
        }

        synchronized(this) {
            return runBlocking {
                try {
                    val newToken = refreshToken(RefreshRequest(refreshToken))
                    dataStore.edit { prefs ->
                        prefs[AuthPrefKeys.ACCESS_TOKEN] = newToken.accessToken.orEmpty()
                        prefs[AuthPrefKeys.REFRESH_TOKEN] = newToken.refreshToken.orEmpty()
                    }
                    response.request
                        .newBuilder()
                        .header("Authorization", "Bearer ${newToken.accessToken}")
                        .build()
                } catch (error: Throwable) {
                    response.close()
                    null
                }
            }
        }
    }

    private suspend fun refreshToken(tokenRequest: RefreshRequest): AuthTokenResponse {
        val interceptor = Interceptor.invoke { chain ->
            val request = chain
                .request()
                .newBuilder()
                .build()
            chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(chuckInterceptor)
            .addInterceptor(interceptor)
            .build()

        val apiService = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(LocalGemApi::class.java)

        try {
            val newRequest = apiService.refresh(tokenRequest)
            dataStore.edit { prefs ->
                prefs[AuthPrefKeys.ACCESS_TOKEN] = newRequest.data?.accessToken.orEmpty()
                prefs[AuthPrefKeys.REFRESH_TOKEN] = newRequest.data?.refreshToken.orEmpty()
            }
            return newRequest.data ?: AuthTokenResponse()
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }
}
