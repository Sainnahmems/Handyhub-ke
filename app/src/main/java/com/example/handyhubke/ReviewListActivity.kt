package com.example.handyhubke

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.handyhubke.adapter.ReviewAdapter
import com.example.handyhubke.data.local.DatabaseHelper
import com.example.handyhubke.data.model.Review
import com.example.handyhubke.databinding.ActivityReviewListBinding

class ReviewListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReviewListBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)
        setupRecyclerView()
        setupSearchView()
    }

    private fun setupRecyclerView() {
        val reviews = dbHelper.getAllReviews()
        adapter = ReviewAdapter(reviews) { review ->
            showReviewOptions(review)
        }
        binding.rvReviews.layoutManager = LinearLayoutManager(this)
        binding.rvReviews.adapter = adapter
    }

    private fun setupSearchView() {
        binding.searchViewReviews.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { performSearch(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { performSearch(it) }
                return true
            }
        })
    }

    private fun performSearch(query: String) {
        val filteredList = if (query.isEmpty()) {
            dbHelper.getAllReviews()
        } else {
            dbHelper.searchReviews(query)
        }
        adapter.updateData(filteredList)
    }

    private fun showReviewOptions(review: Review) {
        val options = arrayOf("Edit Review", "Delete Review")
        AlertDialog.Builder(this)
            .setTitle("Review Options")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> editReview(review)
                    1 -> deleteReview(review)
                }
            }
            .show()
    }

    private fun editReview(review: Review) {
        // Simple example: toggle rating for demo
        val updatedReview = review.copy(rating = if (review.rating < 5f) review.rating + 1f else 1f)
        dbHelper.updateReview(updatedReview)
        refreshList()
        Toast.makeText(this, "Review Updated", Toast.LENGTH_SHORT).show()
    }

    private fun deleteReview(review: Review) {
        review.id?.let {
            dbHelper.deleteReview(it)
            refreshList()
            Toast.makeText(this, "Review Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun refreshList() {
        adapter.updateData(dbHelper.getAllReviews())
    }
}
