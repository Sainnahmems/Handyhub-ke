package com.example.handyhubke.data.api

import com.example.handyhubke.data.repository.HandyHubRepositoryImpl
import com.example.handyhubke.domain.repository.HandyHubRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    // Replace with your actual backend URL
    private const val BASE_URL = "https://api.handyhub.com/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        // Example: Add an Auth Interceptor if token is stored in SharedPreferences
        // .addInterceptor(AuthInterceptor(context))
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    /**
     * Provides the repository instance.
     * In a production app, this would be managed by Dependency Injection (e.g., Hilt).
     */
    fun provideRepository(): HandyHubRepository {
        return HandyHubRepositoryImpl(apiService)
    }
}
