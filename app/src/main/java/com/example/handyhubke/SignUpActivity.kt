package com.example.handyhubke

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.handyhubke.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            if (name.isEmpty()) {
                Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val isCustomer = binding.toggleRoleGroup.checkedButtonId == R.id.btnRoleCustomer
            if (isCustomer) {
                startActivity(Intent(this, CustomerMainActivity::class.java))
            } else {
                Toast.makeText(this, "Worker flow not implemented yet", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }
}