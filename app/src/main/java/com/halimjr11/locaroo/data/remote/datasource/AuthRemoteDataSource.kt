package com.halimjr11.locaroo.data.remote.datasource

import com.halimjr11.locaroo.data.remote.model.AuthData
import com.halimjr11.locaroo.data.remote.model.LoginRequest
import com.halimjr11.locaroo.data.remote.model.RefreshRequest
import com.halimjr11.locaroo.data.remote.model.RegisterRequest
import retrofit2.Response

interface AuthRemoteDataSource {
    suspend fun login(loginRequest: LoginRequest): Response<AuthData>
    suspend fun register(registerRequest: RegisterRequest): Response<AuthData>
    suspend fun refreshToken(refreshRequest: RefreshRequest): Response<AuthData>
    suspend fun getCurrentUser(): Response<AuthData>
}
