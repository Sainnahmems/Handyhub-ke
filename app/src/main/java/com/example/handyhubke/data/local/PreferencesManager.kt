package com.example.handyhubke.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * PreferencesManager: Handles lightweight local persistence using SharedPreferences.
 * Follows a Singleton-like pattern for ease of use across the application.
 */
class PreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "HandyHubPrefs"
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_USER_ROLE = "user_role"
        private const val KEY_THEME_MODE = "theme_mode"
        
        // Role Constants
        const val ROLE_HOMEOWNER = "Homeowner"
        const val ROLE_PROFESSIONAL = "Service Professional"
        
        // Theme Constants
        const val THEME_LIGHT = "light"
        const val THEME_DARK = "dark"
    }

    // --- Authentication Token ---
    fun saveAuthToken(token: String) {
        sharedPreferences.edit { putString(KEY_AUTH_TOKEN, token) }
    }

    fun getAuthToken(): String? = sharedPreferences.getString(KEY_AUTH_TOKEN, null)

    fun clearAuthToken() {
        sharedPreferences.edit { remove(KEY_AUTH_TOKEN) }
    }

    // --- User Role ---
    fun saveUserRole(role: String) {
        sharedPreferences.edit { putString(KEY_USER_ROLE, role) }
    }

    fun getUserRole(): String = sharedPreferences.getString(KEY_USER_ROLE, ROLE_HOMEOWNER) ?: ROLE_HOMEOWNER

    // --- App Configuration (Theme) ---
    fun saveThemeSelection(theme: String) {
        sharedPreferences.edit { putString(KEY_THEME_MODE, theme) }
    }

    fun getThemeSelection(): String = sharedPreferences.getString(KEY_THEME_MODE, THEME_LIGHT) ?: THEME_LIGHT

    // --- Global Logout / Clear ---
    fun clearAll() {
        sharedPreferences.edit { clear() }
    }
}
