package com.example.catapp.core.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> {
    return withContext(Dispatchers.IO) {
        try {
            ApiResult.Success(apiCall())
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Terjadi kesalahan jaringan")
        }
    }
}