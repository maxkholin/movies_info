package com.example.moviesinfo.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesinfo.R
import com.example.moviesinfo.data.models.Review

private const val POSITIVE = "Позитивный"
private const val NEGATIVE = "Негативный"

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val authorTextView = itemView.findViewById<TextView>(R.id.authorTextView)
        val reviewTextView = itemView.findViewById<TextView>(R.id.reviewTextView)
        val linearLayoutReview = itemView.findViewById<LinearLayout>(R.id.linearLayoutReview)
    }

    interface OnLoadMoreListener {
        fun onLoadMore(movieId: Int)
    }

    private var onLoadMoreListener: OnLoadMoreListener? = null

    private var _reviews = listOf<Review>()
    var reviews: List<Review>
        get() = _reviews
        set(value) {
            _reviews = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.review_item,
            parent,
            false
        )
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return _reviews.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = _reviews[position]

        holder.authorTextView.text = review.author
        holder.reviewTextView.text = review.review

        holder.linearLayoutReview.setBackgroundColor(
            setBackgroundColor(
                review.type,
                holder.itemView
            )
        )
        holder.reviewTextView.setTextColor(getTextColor(review.type, holder.itemView))
        holder.authorTextView.setTextColor(getTextColor(review.type, holder.itemView))

        if (position == _reviews.size - 1) {
            onLoadMoreListener?.onLoadMore(review.movieId)
        }
    }

    private fun getTextColor(type: String, view: View): Int {
        return when (type) {
            POSITIVE, NEGATIVE -> ContextCompat
                .getColor(view.context, android.R.color.white)

            else -> ContextCompat
                .getColor(view.context, android.R.color.white)
        }
    }

    private fun setBackgroundColor(type: String, view: View): Int {
        return when (type) {
            POSITIVE -> ContextCompat
                .getColor(view.context, android.R.color.holo_green_light)

            NEGATIVE -> ContextCompat
                .getColor(view.context, android.R.color.holo_red_light)

            else -> ContextCompat
                .getColor(view.context, android.R.color.darker_gray)
        }
    }
}