package com.halimjr11.locaroo.domain.repository

import com.halimjr11.locaroo.domain.model.AuthUser
import kotlinx.coroutines.flow.Flow

interface AuthPreferences {
    // Access Token
    suspend fun saveAccessToken(token: String)
    suspend fun getAccessToken(): String?
    
    // Refresh Token
    suspend fun saveRefreshToken(token: String)
    suspend fun getRefreshToken(): String?
    
    // User Data
    suspend fun saveUser(user: AuthUser)
    suspend fun getUser(): AuthUser?
    
    // Clear all auth data
    suspend fun clearAuthData()
    
    // Auth State
    fun isLoggedIn(): Flow<Boolean>
}
