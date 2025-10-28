package com.halimjr11.locaroo.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

// Expose a single DataStore instance on Context
val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

object AuthPrefKeys {
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    val USER_JSON = stringPreferencesKey("user_json")
}
