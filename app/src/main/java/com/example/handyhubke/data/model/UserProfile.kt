package com.example.handyhubke.data.model

/**
 * UserProfile: Represents a user in the Firebase Firestore database.
 */
data class UserProfile(
    val uid: String = "",
    val fullName: String = "",
    val email: String = "",
    val role: String = "Homeowner", // "Homeowner" or "Service Professional"
    val phoneNumber: String = "",
    val skills: List<String> = emptyList(),
    val hourlyRate: Double = 0.0,
    val rating: Double = 5.0,
    val profileImageUrl: String = ""
)
