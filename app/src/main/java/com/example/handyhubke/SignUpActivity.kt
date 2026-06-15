package com.example.handyhubke

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.handyhubke.data.local.PreferencesManager
import com.example.handyhubke.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        preferencesManager = PreferencesManager(this)

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etSignUpEmail.text.toString().trim()
            val password = binding.etSignUpPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // In a real app, you'd show a progress bar here
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val isCustomer = binding.toggleRoleGroup.checkedButtonId == R.id.btnRoleCustomer
                        val role = if (isCustomer) PreferencesManager.ROLE_HOMEOWNER else PreferencesManager.ROLE_PROFESSIONAL
                        
                        preferencesManager.saveAuthToken(email)
                        preferencesManager.saveUserRole(role)

                        Toast.makeText(this, "Registration Successful as $role", Toast.LENGTH_SHORT).show()

                        if (isCustomer) {
                            startActivity(Intent(this, CustomerMainActivity::class.java))
                        } else {
                            startActivity(Intent(this, WorkerMainActivity::class.java))
                        }
                        finish()
                    } else {
                        Toast.makeText(this, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}