package com.halimjr11.locaroo.data.repository

import android.content.Context
import com.google.gson.Gson
import com.halimjr11.locaroo.data.local.datastore.AuthPrefKeys
import com.halimjr11.locaroo.data.local.datastore.authDataStore
import com.halimjr11.locaroo.domain.model.AuthUser
import com.halimjr11.locaroo.domain.repository.AuthLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.edit

class AuthLocalRepositoryImpl(
    private val appContext: Context,
    private val gson: Gson = Gson()
) : AuthLocalRepository {

    override val accessToken: Flow<String?> = appContext.authDataStore.data.map { prefs ->
        prefs[AuthPrefKeys.ACCESS_TOKEN]
    }

    override val refreshToken: Flow<String?> = appContext.authDataStore.data.map { prefs ->
        prefs[AuthPrefKeys.REFRESH_TOKEN]
    }

    override val user: Flow<AuthUser?> = appContext.authDataStore.data.map { prefs ->
        prefs[AuthPrefKeys.USER_JSON]?.let { json ->
            runCatching { gson.fromJson(json, AuthUser::class.java) }.getOrNull()
        }
    }

    override suspend fun setTokens(access: String?, refresh: String?) {
        appContext.authDataStore.edit { prefs ->
            if (access == null) prefs.remove(AuthPrefKeys.ACCESS_TOKEN) else prefs[AuthPrefKeys.ACCESS_TOKEN] = access
            if (refresh == null) prefs.remove(AuthPrefKeys.REFRESH_TOKEN) else prefs[AuthPrefKeys.REFRESH_TOKEN] = refresh
        }
    }

    override suspend fun setAccessToken(access: String?) {
        appContext.authDataStore.edit { prefs ->
            if (access == null) prefs.remove(AuthPrefKeys.ACCESS_TOKEN) else prefs[AuthPrefKeys.ACCESS_TOKEN] = access
        }
    }

    override suspend fun setRefreshToken(refresh: String?) {
        appContext.authDataStore.edit { prefs ->
            if (refresh == null) prefs.remove(AuthPrefKeys.REFRESH_TOKEN) else prefs[AuthPrefKeys.REFRESH_TOKEN] = refresh
        }
    }

    override suspend fun setUser(user: AuthUser?) {
        appContext.authDataStore.edit { prefs ->
            if (user == null) {
                prefs.remove(AuthPrefKeys.USER_JSON)
            } else {
                prefs[AuthPrefKeys.USER_JSON] = gson.toJson(user)
            }
        }
    }

    override suspend fun clear() {
        appContext.authDataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
