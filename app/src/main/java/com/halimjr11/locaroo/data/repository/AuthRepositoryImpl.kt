package com.halimjr11.locaroo.data.repository

import com.halimjr11.locaroo.data.mapper.AuthMapper
import com.halimjr11.locaroo.data.remote.LocalGemApi
import com.halimjr11.locaroo.domain.model.AuthData
import com.halimjr11.locaroo.domain.model.AuthUser
import com.halimjr11.locaroo.domain.model.LoginRequest
import com.halimjr11.locaroo.domain.model.RefreshRequest
import com.halimjr11.locaroo.domain.model.RegisterRequest
import com.halimjr11.locaroo.domain.model.Result
import com.halimjr11.locaroo.domain.repository.AuthPreferences
import com.halimjr11.locaroo.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: LocalGemApi,
    private val authPreferences: AuthPreferences,
    private val authMapper: AuthMapper
) : AuthRepository, BaseRepository() {

    override suspend fun login(loginRequest: LoginRequest): Result<AuthData> {
        return safeApiCall {
            val response = api.login(
                com.halimjr11.locaroo.data.remote.model.LoginRequest(
                    email = loginRequest.email,
                    password = loginRequest.password
                )
            )
            val authData =
                response.data?.let { authMapper.map(it) } ?: throw Exception("Invalid response")
            saveAuthData(authData)
            authData
        }
    }

    override suspend fun register(registerRequest: RegisterRequest): Result<AuthData> {
        return safeApiCall {
            val response = api.register(
                com.halimjr11.locaroo.data.remote.model.RegisterRequest(
                    username = registerRequest.username,
                    password = registerRequest.password,
                    email = registerRequest.email
                )
            )
            val authData =
                response.data?.let { authMapper.map(it) } ?: throw Exception("Invalid response")
            saveAuthData(authData)
            authData
        }
    }

    override suspend fun refreshToken(refreshRequest: RefreshRequest): Result<AuthData> {
        return safeApiCall {
            val response = api.refresh(
                com.halimjr11.locaroo.data.remote.model.RefreshRequest(
                    refreshToken = refreshRequest.refreshToken
                )
            )
            val authData = response.data?.let {
                // Map AuthTokens to AuthData
                AuthData(
                    accessToken = it.accessToken,
                    refreshToken = it.refreshToken,
                    user = null // User data not included in refresh response
                )
            } ?: throw Exception("Invalid response")

            saveAuthData(authData)
            authData
        }
    }

    override suspend fun getCurrentUser(): Result<AuthUser?> {
        return safeApiCall {
            val token = authPreferences.getAccessToken()
            // Note: The API doesn't have a getCurrentUser endpoint yet
            // For now, we'll just return the user from local storage
            authPreferences.getUser()
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            authPreferences.clearAuthData()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(Exception("Logout failed: ${e.message}", e))
        }
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return authPreferences.getAccessToken() != null
    }

    private suspend fun saveAuthData(authData: AuthData) {
        authData.accessToken?.let { authPreferences.saveAccessToken(it) }
        authData.refreshToken?.let { authPreferences.saveRefreshToken(it) }
        authData.user?.let {
            authPreferences.saveUser(
                AuthUser(
                    id = it.id,
                    username = it.username,
                    email = it.email,
                    name = it.name
                )
            )
        }
    }
}
