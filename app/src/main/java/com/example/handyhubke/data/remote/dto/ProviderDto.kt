package com.example.handyhubke.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProviderDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("category") val category: String,
    @SerializedName("hourly_rate") val hourlyRate: Double,
    @SerializedName("rating") val rating: Double,
    @SerializedName("description") val description: String?,
    @SerializedName("image_url") val imageUrl: String?
)

data class CategoryDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("icon_url") val iconUrl: String?
)
