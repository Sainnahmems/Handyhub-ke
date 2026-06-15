package com.example.handyhubke.util

import java.security.MessageDigest

/**
 * SecurityUtils: Utility class for cryptographic operations.
 */
object SecurityUtils {

    /**
     * Hashes a plain-text password using SHA-256.
     * 
     * NOTE: For production-level security, it is highly recommended to perform 
     * password hashing on the server-side using BCrypt or Argon2. If hashing
     * on the client, ensure a unique salt is used for each user.
     */
    fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}
