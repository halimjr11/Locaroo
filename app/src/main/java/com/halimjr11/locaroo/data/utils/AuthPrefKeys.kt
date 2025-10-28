package com.halimjr11.locaroo.data.utils

import androidx.datastore.preferences.core.stringPreferencesKey
import com.halimjr11.locaroo.data.utils.Constants.ACCESS_TOKEN_KEY
import com.halimjr11.locaroo.data.utils.Constants.REFRESH_TOKEN_KEY
import com.halimjr11.locaroo.data.utils.Constants.USER_EMAIL_KEY
import com.halimjr11.locaroo.data.utils.Constants.USER_NAME_KEY

object AuthPrefKeys {
    val ACCESS_TOKEN = stringPreferencesKey(ACCESS_TOKEN_KEY)
    val REFRESH_TOKEN = stringPreferencesKey(REFRESH_TOKEN_KEY)
    val USER_NAME_JSON = stringPreferencesKey(USER_NAME_KEY)
    val USER_EMAIL_JSON = stringPreferencesKey(USER_EMAIL_KEY)
}