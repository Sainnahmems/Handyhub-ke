package com.example.handyhubke

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var emailLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var progressBar: ProgressBar
    private lateinit var txtRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailLayout = findViewById(R.id.emailLayout)
        passwordLayout = findViewById(R.id.passwordLayout)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        progressBar = findViewById(R.id.progressBar)
        txtRegister = findViewById(R.id.txtRegister)

        btnLogin.setOnClickListener {
            validateAndLogin()
        }

        txtRegister.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun validateAndLogin() {

        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        emailLayout.error = null
        passwordLayout.error = null

        if (email.isEmpty()) {
            emailLayout.error = "Email is required"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.error = "Invalid email address"
            return
        }

        if (password.isEmpty()) {
            passwordLayout.error = "Password is required"
            return
        }

        if (password.length < 6) {
            passwordLayout.error = "Minimum 6 characters"
            return
        }

        progressBar.visibility = View.VISIBLE
        btnLogin.isEnabled = false

        btnLogin.postDelayed({

            progressBar.visibility = View.GONE
            btnLogin.isEnabled = true

            Toast.makeText(
                this,
                "Login Successful",
                Toast.LENGTH_SHORT
            ).show()

            // Navigate to HomeActivity
            startActivity(Intent(this, CustomerMainActivity::class.java))
            finish()

        }, 2000)
    }
}