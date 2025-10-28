package com.halimjr11.locaroo.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.halimjr11.locaroo.data.remote.model.UserDto
import com.halimjr11.locaroo.domain.model.AuthUser
import com.halimjr11.locaroo.domain.repository.AuthPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthPreferencesImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson = Gson()
) : AuthPreferences {

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

    override suspend fun saveUser(user: AuthUser) {
        dataStore.edit { prefs ->
            val userJson = gson.toJson(
                UserDto(
                    id = user.id,
                    username = user.username,
                    email = user.email,
                    name = user.name
                )
            )
            prefs[AuthPrefKeys.USER_JSON] = userJson
        }
    }

    override suspend fun getUser(): AuthUser? {
        val userJson = dataStore.data.map { it[AuthPrefKeys.USER_JSON] }.first()
        return userJson?.let { jsonString ->
            try {
                val userDto = gson.fromJson(jsonString, UserDto::class.java)
                AuthUser(
                    id = userDto.id ?: 0,
                    username = userDto.username,
                    email = userDto.email ?: "",
                    name = userDto.name
                )
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun clearAuthData() {
        dataStore.edit { prefs ->
            prefs.remove(AuthPrefKeys.ACCESS_TOKEN)
            prefs.remove(AuthPrefKeys.REFRESH_TOKEN)
            prefs.remove(AuthPrefKeys.USER_JSON)
        }
    }

    override fun isLoggedIn(): Flow<Boolean> {
        return dataStore.data.map { it[AuthPrefKeys.ACCESS_TOKEN] != null }
    }
}
