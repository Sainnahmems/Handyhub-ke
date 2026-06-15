package com.example.handyhubke.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.handyhubke.R
import com.example.handyhubke.data.model.Review

class ReviewAdapter(
    private var reviews: List<Review>,
    private val onReviewClick: (Review) -> Unit
) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvWorkerName: TextView = view.findViewById(R.id.tvWorkerName)
        val rbRating: RatingBar = view.findViewById(R.id.rbRating)
        val tvComment: TextView = view.findViewById(R.id.tvComment)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.tvWorkerName.text = review.workerName
        holder.rbRating.rating = review.rating
        holder.tvComment.text = review.comment
        holder.tvDate.text = review.date

        holder.itemView.setOnClickListener { onReviewClick(review) }
    }

    override fun getItemCount() = reviews.size

    fun updateData(newReviews: List<Review>) {
        this.reviews = newReviews
        notifyDataSetChanged()
    }
}
