package com.example.handyhubke.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BookingDto(
    @SerializedName("id") val id: String,
    @SerializedName("client_id") val clientId: String,
    @SerializedName("provider_id") val providerId: String,
    @SerializedName("service_type") val serviceType: String,
    @SerializedName("date") val date: String,
    @SerializedName("status") val status: String, // "Pending", "In Progress", "Completed", "Cancelled"
    @SerializedName("price") val price: Double
)

data class BookingRequest(
    @SerializedName("provider_id") val providerId: String,
    @SerializedName("service_type") val serviceType: String,
    @SerializedName("date") val date: String,
    @SerializedName("price") val price: Double
)

data class BookingStatusUpdate(
    @SerializedName("status") val status: String
)
