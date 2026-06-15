package com.example.handyhubke

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.handyhubke.data.model.JobRequest
import com.example.handyhubke.databinding.ActivityPaymentscreenBinding

class PaymentScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentscreenBinding
    private var pendingJob: JobRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pendingJob = intent.getSerializableExtra("PENDING_JOB") as? JobRequest

        binding.btnPay.setOnClickListener {
            val checkedId = binding.rgPaymentMethods.checkedRadioButtonId
            if (checkedId == -1) {
                Toast.makeText(this, "Select payment options", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Processing Secure Transaction API Request...", Toast.LENGTH_SHORT).show()
            
            val intent = Intent(this, PaymentConfirmationActivity::class.java).apply {
                putExtra("PENDING_JOB", pendingJob)
            }
            startActivity(intent)
            finish()
        }
    }
}
