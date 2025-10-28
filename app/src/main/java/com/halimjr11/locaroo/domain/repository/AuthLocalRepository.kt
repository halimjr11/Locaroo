package com.halimjr11.locaroo.domain.repository

import com.halimjr11.locaroo.domain.model.AuthUser
import kotlinx.coroutines.flow.Flow

interface AuthLocalRepository {
    // Observables
    val accessToken: Flow<String?>
    val refreshToken: Flow<String?>
    val user: Flow<AuthUser?>

    // Mutations
    suspend fun setTokens(access: String?, refresh: String?)
    suspend fun setAccessToken(access: String?)
    suspend fun setRefreshToken(refresh: String?)
    suspend fun setUser(user: AuthUser?)
    suspend fun clear()
}
