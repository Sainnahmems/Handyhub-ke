package com.example.handyhubke.data.repository

import com.example.handyhubke.data.api.RetrofitClient
import com.example.handyhubke.data.model.Worker
import com.example.handyhubke.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class HandymanRepository {

    suspend fun fetchHandymen(): Resource<List<Worker>> = withContext(Dispatchers.IO) {
        try {
            val response = RetrofitClient.apiService.getHandymen()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Resource.Success(body)
                } else {
                    Resource.Error("Empty response body")
                }
            } else {
                val errorMsg = when (response.code()) {
                    401 -> "Unauthorized access. Please log in again."
                    404 -> "Service professionals list not found."
                    500 -> "Server error. Please try again later."
                    else -> "Unexpected error: ${response.code()}"
                }
                Resource.Error(errorMsg)
            }
        } catch (e: IOException) {
            Resource.Error("Network failure: Please check your internet connection.")
        } catch (e: Exception) {
            Resource.Error("An unknown error occurred: ${e.localizedMessage}")
        }
    }
}
