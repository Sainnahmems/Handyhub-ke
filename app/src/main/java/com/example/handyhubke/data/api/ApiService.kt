package com.example.handyhubke.data.api

import com.example.handyhubke.data.model.UserResponse
import com.example.handyhubke.data.model.Worker
import com.example.handyhubke.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

/**
 * Retrofit API interface defining the RESTful endpoints for HandyHub.
 */
interface ApiService {

    // --- Authentication ---
    
    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    // --- Services & Providers ---

    @GET("api/services")
    suspend fun getCategories(): Response<List<CategoryDto>>

    @GET("api/providers")
    suspend fun getProviders(
        @Query("category") category: String? = null
    ): Response<List<ProviderDto>>

    @GET("api/handymen")
    suspend fun getHandymen(): Response<List<Worker>>

    // --- Bookings ---

    @POST("api/bookings")
    suspend fun createBooking(@Body request: BookingRequest): Response<BookingDto>

    @GET("api/bookings")
    suspend fun getMyBookings(): Response<List<BookingDto>>

    @GET("https://jsonplaceholder.typicode.com/users")
    suspend fun getUsers(): Response<List<UserResponse>>

    @PUT("api/bookings/{id}")
    suspend fun updateBookingStatus(
        @Path("id") id: String,
        @Body statusUpdate: BookingStatusUpdate
    ): Response<BookingDto>

    @DELETE("api/bookings/{id}")
    suspend fun cancelBooking(@Path("id") id: String): Response<Unit>
}
