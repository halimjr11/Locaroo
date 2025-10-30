package com.halimjr11.locaroo.domain.repository

import com.halimjr11.locaroo.domain.model.AuthDataDomain
import com.halimjr11.locaroo.domain.model.AuthUserDomain
import com.halimjr11.locaroo.domain.utils.DomainResult

interface AuthRemoteRepository {
    suspend fun login(
        email: String,
        password: String
    ): DomainResult<AuthDataDomain>

    suspend fun register(
        username: String,
        email: String,
        password: String
    ): DomainResult<AuthDataDomain>

    suspend fun getCurrentUser(): DomainResult<AuthUserDomain?>
}
