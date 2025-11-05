package com.halimjr11.locaroo.domain.repository

import com.halimjr11.locaroo.data.remote.model.UserResponse
import kotlinx.coroutines.flow.Flow

interface AuthLocalRepository {
    /**
     * Saves the given access token to the local storage.
     * @param token The access token to be saved.
     */
    suspend fun saveAccessToken(token: String)

    /**
     * Retrieves the access token saved in the local storage.
     * @return The saved access token, or an empty string if no token is saved.
     */
    suspend fun getAccessToken(): String?

    /**
     * Saves the given refresh token to the local storage.
     * @param token The refresh token to be saved.
     */
    suspend fun saveRefreshToken(token: String)

    /**
     * Retrieves the refresh token saved in the local storage.
     * @return The saved refresh token, or an empty string if no token is saved.
     */
    suspend fun getRefreshToken(): String?

    /**
     * Saves the given user data to the local storage.
     * @param user The user data to be saved.
     */
    suspend fun saveUser(user: UserResponse)

    /**
     * Retrieves the username saved in the local storage.
     * @return The saved username, or an empty string if no username is saved.
     */
    suspend fun getUserName(): String?

    /**
     * Retrieves the email saved in the local storage.
     * @return The saved email, or an empty string if no email is saved.
     */
    suspend fun getUserEmail(): String?

    /**
     * Clears all authentication data saved in the local storage.
     * This includes the access token, refresh token, username, and email.
     */
    suspend fun clearAuthData()

    /**
     * A Flow that emits a boolean indicating whether the user is logged in or not.
     * This is determined by checking if the access token is not null.
     * @return A Flow that emits a boolean indicating whether the user is logged in or not.
     */
    fun isLoggedIn(): Flow<Boolean?>
}
