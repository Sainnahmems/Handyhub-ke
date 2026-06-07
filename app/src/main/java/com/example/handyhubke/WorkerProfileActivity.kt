package com.example.handyhubke

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.handyhubke.data.model.Worker
import com.example.handyhubke.databinding.ActivityWorkerProfileBinding

class WorkerProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkerProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkerProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val worker = intent.getSerializableExtra("DATA_WORKER") as? Worker

        worker?.let {
            binding.profileName.text = it.name
            binding.profileBio.text = it.description
            binding.profileRate.text = "$${it.hourlyRate} / hour"
        }

        binding.btnBookNow.setOnClickListener {
            val intent = Intent(this, BookingActivity::class.java).apply {
                putExtra("DATA_WORKER", worker)
            }
            startActivity(intent)
        }
    }
}