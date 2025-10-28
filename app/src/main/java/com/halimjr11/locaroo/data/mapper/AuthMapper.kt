package com.halimjr11.locaroo.data.mapper

import com.halimjr11.locaroo.data.remote.model.AuthData as AuthDataDto
import com.halimjr11.locaroo.data.remote.model.UserDto
import com.halimjr11.locaroo.domain.model.AuthData
import com.halimjr11.locaroo.domain.model.AuthUser
import javax.inject.Inject

class AuthMapper @Inject constructor() : TwoWayMapper<AuthDataDto, AuthData> {
    override fun map(input: AuthDataDto): AuthData {
        return AuthData(
            accessToken = input.accessToken,
            refreshToken = input.refreshToken,
            user = input.user?.let { userDto ->
                AuthUser(
                    id = userDto.id ?: 0,
                    username = userDto.username,
                    email = userDto.email ?: "",
                    name = userDto.name
                )
            }
        )
    }

    override fun reverseMap(input: AuthData): AuthDataDto {
        return AuthDataDto(
            accessToken = input.accessToken,
            refreshToken = input.refreshToken,
            user = input.user?.let { user ->
                UserDto(
                    id = user.id,
                    username = user.username,
                    email = user.email,
                    name = user.name
                )
            }
        )
    }
}

fun UserDto.toDomain(): AuthUser {
    return AuthUser(
        id = this.id ?: 0,
        username = this.username,
        email = this.email ?: "",
        name = this.name
    )
}

fun AuthUser.toDto(): UserDto {
    return UserDto(
        id = this.id,
        username = this.username,
        email = this.email,
        name = this.name
    )
}
