package com.halimjr11.locaroo.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.halimjr11.locaroo.data.mapper.RemoteDataMapper
import com.halimjr11.locaroo.data.remote.LocalGemApi
import com.halimjr11.locaroo.data.remote.model.request.LoginRequest
import com.halimjr11.locaroo.data.remote.model.request.RegisterRequest
import com.halimjr11.locaroo.data.utils.AuthPrefKeys
import com.halimjr11.locaroo.domain.model.AuthDataDomain
import com.halimjr11.locaroo.domain.model.AuthUserDomain
import com.halimjr11.locaroo.domain.utils.DomainResult
import com.halimjr11.locaroo.domain.repository.AuthRemoteRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRemoteRepositoryImpl @Inject constructor(
    private val api: LocalGemApi,
    private val dataStore: DataStore<Preferences>,
    private val authMapper: RemoteDataMapper
) : AuthRemoteRepository, BaseRepository() {


    override suspend fun login(email: String, password: String): DomainResult<AuthDataDomain> {
        return safeApiCall {
            val response = api.login(
                body = LoginRequest(
                    email = email,
                    password = password
                )
            )
            response.data?.let {
                authMapper.mapAuthToDomain(it)
            } ?: AuthDataDomain()
        }
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): DomainResult<AuthDataDomain> {
        return safeApiCall {
            val response = api.register(
                body = RegisterRequest(
                    username = username,
                    email = email,
                    password = password
                )
            )
            response.data?.let { authMapper.mapAuthToDomain(it) } ?: AuthDataDomain()
        }
    }

    override suspend fun getCurrentUser(): DomainResult<AuthUserDomain?> {
        return safeApiCall {
            val name = dataStore.data.map { it[AuthPrefKeys.USER_NAME_JSON] }.first()
            val email = dataStore.data.map { it[AuthPrefKeys.USER_EMAIL_JSON] }.first()
            AuthUserDomain(
                name = name.orEmpty(),
                email = email.orEmpty()
            )
        }
    }
}
