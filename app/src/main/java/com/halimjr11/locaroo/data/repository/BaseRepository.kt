package com.halimjr11.locaroo.data.repository

import com.halimjr11.locaroo.domain.model.DomainResult
import retrofit2.HttpException
import java.io.IOException

open class BaseRepository {
    
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): DomainResult<T> {
        return try {
            DomainResult.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> DomainResult.Error(Exception("Network error: ${throwable.message}"))
                is HttpException -> {
                    val errorResponse = convertErrorBody(throwable)
                    DomainResult.Error(
                        Exception(errorResponse?.message ?: "Something went wrong")
                    )
                }
                else -> {
                    DomainResult.Error(Exception("Unknown error: ${throwable.message}"))
                }
            }
        }
    }

    private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
        return try {
            throwable.response()?.errorBody()?.source()?.let {
                // Here you can parse your error response if needed
                // For now, returning a simple error response
                ErrorResponse("Error code: ${throwable.code()}")
            }
        } catch (exception: Exception) {
            null
        }
    }
}

data class ErrorResponse(val message: String)
