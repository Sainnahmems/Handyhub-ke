package com.example.handyhubke

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.handyhubke.data.local.DatabaseHelper
import com.example.handyhubke.data.model.Review
import com.example.handyhubke.databinding.ActivityReviewactivityBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewactivityBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        binding.btnSubmitReview.setOnClickListener {
            val stars = binding.ratingBar.rating
            val comment = binding.etReviewComments.text.toString()
            
            if (stars == 0f) {
                Toast.makeText(this, "Please provide a rating", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
            val review = Review(
                workerName = "Selected Worker", // This would normally come from intent extras
                rating = stars,
                comment = comment,
                date = currentDate,
            )

            val id = dbHelper.insertReview(review)
            if (id != -1L) {
                val message = getString(R.string.review_submitted, stars)
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to submit review", Toast.LENGTH_SHORT).show()
            }
        }
    }
}