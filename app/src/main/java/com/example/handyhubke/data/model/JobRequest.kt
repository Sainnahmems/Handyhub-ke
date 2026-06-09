package com.example.handyhubke.data.model

import java.io.Serializable

data class JobRequest(
    val id: Int? = null,
    val serviceType: String,
    val description: String,
    val budget: Double,
    val status: String
) : Serializable
