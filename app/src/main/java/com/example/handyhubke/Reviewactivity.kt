package com.example.handyhubke

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.handyhubke.databinding.ActivityReviewactivityBinding

class ReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmitReview.setOnClickListener {
            val stars = binding.ratingBar.rating
            
            if (stars == 0f) {
                Toast.makeText(this, "Please provide a rating", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val message = getString(R.string.review_submitted, stars)
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
