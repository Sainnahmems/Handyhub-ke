package com.example.handyhubke.data.model

import com.google.firebase.Timestamp

/**
 * Booking: Represents a service booking in Firebase Firestore.
 */
data class Booking(
    val id: String = "",
    val customerId: String = "",
    val providerId: String = "",
    val serviceType: String = "",
    val description: String = "",
    val status: String = "Pending", // Pending, In Progress, Completed, Cancelled
    val scheduledDate: Timestamp = Timestamp.now(),
    val totalPrice: Double = 0.0
)
