package com.halimjr11.locaroo.domain.repository

import com.halimjr11.locaroo.data.remote.model.UserResponse
import kotlinx.coroutines.flow.Flow

interface AuthLocalRepository {
    suspend fun saveAccessToken(token: String)
    suspend fun getAccessToken(): String?
    suspend fun saveRefreshToken(token: String)
    suspend fun getRefreshToken(): String?
    suspend fun saveUser(user: UserResponse)
    suspend fun getUserName(): String?
    suspend fun getUserEmail(): String?
    suspend fun clearAuthData()
    fun isLoggedIn(): Flow<Boolean>
}
