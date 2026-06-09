package com.example.handyhubke.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Worker(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("specialty")
    val category: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("hourly_rate")
    val hourlyRate: Double,
    @SerializedName("distance")
    val distance: Double,
    @SerializedName("description")
    val description: String,
    @SerializedName("image_url")
    val imageUrl: String
) : Serializable