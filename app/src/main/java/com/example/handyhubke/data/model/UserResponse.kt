package com.example.handyhubke.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String,
    val company: Company
)

data class Company(
    val name: String,
    val catchPhrase: String,
    val bs: String
)
