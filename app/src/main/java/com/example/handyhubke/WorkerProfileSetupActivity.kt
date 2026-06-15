package com.example.handyhubke

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.handyhubke.databinding.ActivityWorkersprofilesetupBinding

class WorkerProfileSetupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkersprofilesetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkersprofilesetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUploadPhoto.setOnClickListener {
            Toast.makeText(this, "Internal Storage Access Pipeline Activated", Toast.LENGTH_SHORT).show()
        }

        binding.btnSaveSetup.setOnClickListener {
            if(binding.etSkills.text.toString().isEmpty() || binding.etRate.text.toString().isEmpty()){
                Toast.makeText(this, "Missing functional parameter fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Navigate to ServiceProvidersActivity
            startActivity(Intent(this, ServiceProvidersActivity::class.java))
            finish()
        }
    }
}
