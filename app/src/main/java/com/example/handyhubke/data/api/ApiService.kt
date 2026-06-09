package com.example.handyhubke.data.api

import com.example.handyhubke.data.model.Worker
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("api/v1/handymen")
    suspend fun getHandymen(): Response<List<Worker>>
}
