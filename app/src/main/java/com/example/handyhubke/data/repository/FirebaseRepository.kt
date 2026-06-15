package com.example.handyhubke.data.repository

import com.example.handyhubke.data.model.Booking
import com.example.handyhubke.data.model.UserProfile
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

/**
 * FirebaseRepository: Handles remote CRUD operations with Firestore.
 * Implements clean architecture principles for data access.
 */
class FirebaseRepository {

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")
    private val bookingsCollection = db.collection("bookings")

    // --- CREATE ---

    /**
     * Registers a new user profile in Firestore.
     */
    suspend fun createUserProfile(user: UserProfile): Result<Unit> {
        return try {
            usersCollection.document(user.uid).set(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Creates a new service booking request.
     */
    suspend fun createBooking(booking: Booking): Result<String> {
        return try {
            val docRef = bookingsCollection.document()
            val bookingWithId = booking.copy(id = docRef.id)
            docRef.set(bookingWithId).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // --- READ ---

    /**
     * Fetches a user profile from Firestore by UID.
     */
    suspend fun getUserProfile(uid: String): Result<UserProfile?> {
        return try {
            val snapshot = usersCollection.document(uid).get().await()
            val user = snapshot.toObject(UserProfile::class.java)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Fetches a list of available service providers by category.
     */
    suspend fun getServiceProviders(category: String): Result<List<UserProfile>> {
        return try {
            val snapshot = usersCollection
                .whereEqualTo("role", "Service Professional")
                .whereArrayContains("skills", category)
                .get()
                .await()
            val providers = snapshot.toObjects(UserProfile::class.java)
            Result.success(providers)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Retrieves real-time stream of a user's active bookings.
     */
    fun getUserBookingsFlow(userId: String): Flow<List<Booking>> = callbackFlow {
        val registration = bookingsCollection
            .whereEqualTo("customerId", userId)
            .orderBy("scheduledDate", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val bookings = snapshot.toObjects(Booking::class.java)
                    trySend(bookings)
                }
            }
        awaitClose { registration.remove() }
    }

    // --- UPDATE ---

    /**
     * Updates service provider's profile data (e.g., rates, skills).
     */
    suspend fun updateProviderProfile(uid: String, updates: Map<String, Any>): Result<Unit> {
        return try {
            usersCollection.document(uid).update(updates).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Updates a booking's workflow status.
     */
    suspend fun updateBookingStatus(bookingId: String, newStatus: String): Result<Unit> {
        return try {
            bookingsCollection.document(bookingId).update("status", newStatus).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // --- DELETE ---

    /**
     * Cancels/Removes an upcoming service booking.
     */
    suspend fun cancelBooking(bookingId: String): Result<Unit> {
        return try {
            bookingsCollection.document(bookingId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
