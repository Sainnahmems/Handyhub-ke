package com.example.handyhubke

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.handyhubke.databinding.ActivityLoginactivityBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check if user is already logged in
        val sharedPref = getSharedPreferences("HandyHubPrefs", MODE_PRIVATE)
        if (sharedPref.getBoolean("isLoggedIn", false)) {
            startActivity(Intent(this, CustomerMainActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            validateAndLogin()
        }

        binding.txtRegister.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun validateAndLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        binding.emailLayout.error = null
        binding.passwordLayout.error = null

        if (email.isEmpty()) {
            binding.emailLayout.error = "Email is required"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailLayout.error = "Invalid email address"
            return
        }

        if (password.isEmpty()) {
            binding.passwordLayout.error = "Password is required"
            return
        }

        if (password.length < 6) {
            binding.passwordLayout.error = "Minimum 6 characters"
            return
        }

        binding.progressBar.visibility = View.VISIBLE
        binding.btnLogin.isEnabled = false

        // Simulate network call
        binding.btnLogin.postDelayed({
            binding.progressBar.visibility = View.GONE
            binding.btnLogin.isEnabled = true

            // Save login status and a mock username
            val sharedPrefs = getSharedPreferences("HandyHubPrefs", MODE_PRIVATE)
            sharedPrefs.edit {
                putBoolean("isLoggedIn", true)
                putString("username", email.substringBefore("@"))
            }

            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CustomerMainActivity::class.java))
            finish()
        }, 1500)
    }
}
