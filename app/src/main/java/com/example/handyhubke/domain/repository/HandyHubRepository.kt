package com.example.handyhubke.domain.repository

import com.example.handyhubke.data.remote.dto.*
import com.example.handyhubke.util.NetworkResult

/**
 * Domain-level Repository interface for HandyHub business logic.
 * This separates the Data layer from the Presentation/Domain layers.
 */
interface HandyHubRepository {
    
    suspend fun login(request: LoginRequest): NetworkResult<AuthResponse>
    
    suspend fun register(request: RegisterRequest): NetworkResult<AuthResponse>
    
    suspend fun getCategories(): NetworkResult<List<CategoryDto>>
    
    suspend fun getProviders(category: String?): NetworkResult<List<ProviderDto>>
    
    suspend fun createBooking(request: BookingRequest): NetworkResult<BookingDto>
    
    suspend fun updateBookingStatus(id: String, status: String): NetworkResult<BookingDto>
    
    suspend fun cancelBooking(id: String): NetworkResult<Unit>
}
