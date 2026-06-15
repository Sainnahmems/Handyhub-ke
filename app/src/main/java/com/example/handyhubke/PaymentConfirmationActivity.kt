package com.example.handyhubke

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.handyhubke.data.local.DatabaseHelper
import com.example.handyhubke.data.model.JobRequest
import com.example.handyhubke.databinding.ActivityPaymentconfirmationBinding

class PaymentConfirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentconfirmationBinding
    private lateinit var dbHelper: DatabaseHelper
    private var pendingJob: JobRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentconfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)
        pendingJob = intent.getSerializableExtra("PENDING_JOB") as? JobRequest
        
        // Save the job to local database now that payment is "confirmed"
        pendingJob?.let {
            dbHelper.insertJobRequest(it)
        }

        binding.btnDone.setOnClickListener {
            val intent = Intent(this, CustomerMainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
    }
}
