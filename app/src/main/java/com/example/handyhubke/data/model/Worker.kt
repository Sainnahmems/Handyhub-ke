package com.example.handyhubke.data.model

import java.io.Serializable

data class Worker(
    val id: String,
    val name: String,
    val category: String,
    val rating: Double,
    val hourlyRate: Double,
    val distance: Double,
    val description: String,
    val imageUrl: String
) : Serializable