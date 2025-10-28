package com.halimjr11.locaroo.domain.repository

import com.halimjr11.locaroo.domain.model.AuthData
import com.halimjr11.locaroo.domain.model.AuthUser
import com.halimjr11.locaroo.domain.model.Result
import com.halimjr11.locaroo.domain.model.LoginRequest
import com.halimjr11.locaroo.domain.model.RegisterRequest
import com.halimjr11.locaroo.domain.model.RefreshRequest

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): Result<AuthData>
    suspend fun register(registerRequest: RegisterRequest): Result<AuthData>
    suspend fun refreshToken(refreshRequest: RefreshRequest): Result<AuthData>
    suspend fun getCurrentUser(): Result<AuthUser?>
    suspend fun logout(): Result<Unit>
    suspend fun isUserLoggedIn(): Boolean
}
