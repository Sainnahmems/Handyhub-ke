package com.example.handyhubke

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.handyhubke.data.local.DatabaseHelper
import com.example.handyhubke.data.model.JobRequest
import com.example.handyhubke.data.model.Worker
import com.example.handyhubke.databinding.ActivityBookingBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class BookingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookingBinding
    private var selectedDate = ""
    private var selectedTime = ""
    private var currentWorker: Worker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentWorker = intent.getSerializableExtra("DATA_WORKER") as? Worker

        binding.btnDatePicker.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker().build()
            picker.show(supportFragmentManager, "DATE")
            picker.addOnPositiveButtonClickListener {
                selectedDate = picker.headerText
                binding.btnDatePicker.text = selectedDate
            }
        }

        binding.btnTimePicker.setOnClickListener {
            val picker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H).build()
            picker.show(supportFragmentManager, "TIME")
            picker.addOnPositiveButtonClickListener {
                selectedTime = "${picker.hour}:${picker.minute}"
                binding.btnTimePicker.text = selectedTime
            }
        }

        binding.btnProceedPayment.setOnClickListener {
            if (selectedDate.isEmpty() || selectedTime.isEmpty() || binding.etLocation.text.toString().isEmpty()) {
                Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val serviceType = currentWorker?.category ?: "General Service"
            val description = binding.etJobDesc.text.toString().ifEmpty { "Service at ${binding.etLocation.text}" }
            val budget = currentWorker?.hourlyRate ?: 0.0

            val jobRequest = JobRequest(
                serviceType = serviceType,
                description = description,
                budget = budget,
                status = "Pending"
            )

            val intent = Intent(this, PaymentScreenActivity::class.java).apply {
                putExtra("PENDING_JOB", jobRequest)
            }
            startActivity(intent)
            finish()
        }
    }
}
