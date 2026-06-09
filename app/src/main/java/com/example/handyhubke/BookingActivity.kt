package com.example.handyhubke

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.handyhubke.databinding.ActivityBookingBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class BookingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookingBinding
    private var selectedDate = ""
    private var selectedTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // val worker = intent.getSerializableExtra("DATA_WORKER") as? Worker

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
            // Navigate to Paymentscreen
            val intent = Intent(this, Paymentscreen::class.java)
            startActivity(intent)
            finish()
        }
    }
}