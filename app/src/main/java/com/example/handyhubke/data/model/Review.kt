package com.example.handyhubke.data.model

import java.io.Serializable

data class Review(
    val id: Int? = null,
    val workerName: String,
    val rating: Float,
    val comment: String,
    val date: String
) : Serializable
