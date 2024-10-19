package com.example.moviesinfo.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import com.example.moviesinfo.R
import com.example.moviesinfo.data.models.Movie

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImageView = itemView.findViewById<ImageView>(R.id.posterImageView)
        val ratingTextView = itemView.findViewById<TextView>(R.id.ratingTextView)
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

    interface OnMovieClickListener {
        fun onMovieClick(movie: Movie)
    }

    private var _movies = listOf<Movie>()
    var movies: List<Movie>
        get() = _movies
        set(value) {
            _movies = value
            notifyDataSetChanged()
        }

    private var onLoadMoreListener: OnLoadMoreListener? = null
    private var onMovieClickListener: OnMovieClickListener? = null

    fun setOnLoadMoreListener(listener: OnLoadMoreListener) {
        this.onLoadMoreListener = listener
    }

    fun setonMovieClickListener(listener: OnMovieClickListener) {
        this.onMovieClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.movie_item,
            parent,
            false
        )
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return _movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = _movies[position]

        val poster = movie.poster.url

        val rating = movie.rating.rating
        val ratingBackgroundId = getRatingBackgroundId(rating)
        val ratingBackground =
            ContextCompat.getDrawable(holder.itemView.context, ratingBackgroundId)

        holder.posterImageView.load(poster) {
            crossfade(true)
            error(R.drawable.error_image)
        }

        holder.ratingTextView.background = ratingBackground
        holder.ratingTextView.text = String.format("%.1f", rating)

        if (position >= _movies.size - 10) {
            onLoadMoreListener?.onLoadMore()
        }

        holder.itemView.setOnClickListener{
            onMovieClickListener?.onMovieClick(movie)
        }
    }

    private fun getRatingBackgroundId(rating: Double): Int {
        return if (rating > 9) {
            R.drawable.circle_great
        } else if (rating > 7.5) {
            R.drawable.circle_good
        } else if (rating > 6) {
            R.drawable.circle_avg
        } else {
            R.drawable.circle_bad
        }
    }

}