package com.example.handyhubke.data.repository

import com.example.handyhubke.data.api.ApiService
import com.example.handyhubke.data.remote.dto.*
import com.example.handyhubke.domain.repository.HandyHubRepository
import com.example.handyhubke.util.NetworkResult
import retrofit2.Response

/**
 * Implementation of [HandyHubRepository] that fetches data from the [ApiService].
 * Uses Kotlin Coroutines to execute network calls asynchronously.
 */
class HandyHubRepositoryImpl(
    private val api: ApiService
) : HandyHubRepository {

    override suspend fun login(request: LoginRequest): NetworkResult<AuthResponse> {
        return safeApiCall { api.login(request) }
    }

    override suspend fun register(request: RegisterRequest): NetworkResult<AuthResponse> {
        return safeApiCall { api.register(request) }
    }

    override suspend fun getCategories(): NetworkResult<List<CategoryDto>> {
        return safeApiCall { api.getCategories() }
    }

    override suspend fun getProviders(category: String?): NetworkResult<List<ProviderDto>> {
        return safeApiCall { api.getProviders(category) }
    }

    override suspend fun createBooking(request: BookingRequest): NetworkResult<BookingDto> {
        return safeApiCall { api.createBooking(request) }
    }

    override suspend fun updateBookingStatus(id: String, status: String): NetworkResult<BookingDto> {
        return safeApiCall { api.updateBookingStatus(id, BookingStatusUpdate(status)) }
    }

    override suspend fun cancelBooking(id: String): NetworkResult<Unit> {
        return safeApiCall { api.cancelBooking(id) }
    }

    /**
     * Helper function to safely execute API calls and handle errors.
     */
    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null || response.code() == 204) {
                    @Suppress("UNCHECKED_CAST")
                    NetworkResult.Success(body ?: Unit as T)
                } else {
                    NetworkResult.Error("Response body is null")
                }
            } else {
                NetworkResult.Error("Error ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
    }
}
