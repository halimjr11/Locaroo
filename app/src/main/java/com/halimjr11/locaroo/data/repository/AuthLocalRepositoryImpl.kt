package com.halimjr11.locaroo.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import com.halimjr11.locaroo.data.remote.model.UserResponse
import com.halimjr11.locaroo.data.utils.AuthPrefKeys
import com.halimjr11.locaroo.domain.repository.AuthLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AuthLocalRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson = Gson()
) : AuthLocalRepository {
    override suspend fun saveAccessToken(token: String) {
        dataStore.edit { prefs ->
            prefs[AuthPrefKeys.ACCESS_TOKEN] = token
        }
    }

    override suspend fun getAccessToken(): String? {
        return dataStore.data.map { it[AuthPrefKeys.ACCESS_TOKEN] }.first()
    }

    override suspend fun saveRefreshToken(token: String) {
        dataStore.edit { prefs ->
            prefs[AuthPrefKeys.REFRESH_TOKEN] = token
        }
    }

    override suspend fun getRefreshToken(): String? {
        return dataStore.data.map { it[AuthPrefKeys.REFRESH_TOKEN] }.first()
    }

    override suspend fun saveUser(user: UserResponse) {
        dataStore.edit { prefs ->
            prefs[AuthPrefKeys.USER_NAME_JSON] = user.name.orEmpty()
            prefs[AuthPrefKeys.USER_EMAIL_JSON] = user.email.orEmpty()
        }
    }

    override suspend fun getUserName(): String? {
        return dataStore.data.map { it[AuthPrefKeys.USER_NAME_JSON] }.first()
    }

    override suspend fun getUserEmail(): String? {
        return dataStore.data.map { it[AuthPrefKeys.USER_EMAIL_JSON] }.first()
    }

    override suspend fun clearAuthData() {
        dataStore.edit { prefs ->
            prefs.remove(AuthPrefKeys.ACCESS_TOKEN)
            prefs.remove(AuthPrefKeys.REFRESH_TOKEN)
            prefs.remove(AuthPrefKeys.USER_NAME_JSON)
            prefs.remove(AuthPrefKeys.USER_EMAIL_JSON)
        }
    }

    override fun isLoggedIn(): Flow<Boolean> {
        return dataStore.data.map { it[AuthPrefKeys.ACCESS_TOKEN] != null }
    }
}
