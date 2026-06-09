package com.example.handyhubke.data.model

data class Message(
    val id: String,
    val senderId: String,
    val text: String,
    val timestamp: Long,
    val isFromMe: Boolean
)
