package com.example.handyhubke

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.handyhubke.data.local.PreferencesManager
import com.example.handyhubke.data.repository.FirebaseRepository
import com.example.handyhubke.databinding.ActivityLoginactivityBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginactivityBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var preferencesManager: PreferencesManager
    private val firebaseRepository = FirebaseRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        preferencesManager = PreferencesManager(this)

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

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                binding.progressBar.visibility = View.GONE
                binding.btnLogin.isEnabled = true

                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val isCustomer = binding.toggleRoleGroup.checkedButtonId == R.id.btnRoleCustomer
                    val role = if (isCustomer) PreferencesManager.ROLE_HOMEOWNER else PreferencesManager.ROLE_PROFESSIONAL

                    lifecycleScope.launch {
                        user?.let {
                            val result = firebaseRepository.getUserProfile(it.uid)
                            val userProfile = result.getOrNull()
                            
                            // Update local info from Firestore if it exists
                            val displayName = userProfile?.fullName ?: email.substringBefore("@")
                            preferencesManager.saveAuthToken(displayName) 
                        }
                        
                        preferencesManager.saveUserRole(role)

                        Toast.makeText(this@LoginActivity, "Login Successful as $role", Toast.LENGTH_SHORT).show()
                        
                        if (isCustomer) {
                            startActivity(Intent(this@LoginActivity, CustomerMainActivity::class.java))
                        } else {
                            startActivity(Intent(this@LoginActivity, WorkerMainActivity::class.java))
                        }
                        finish()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Authentication Failed: ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}
