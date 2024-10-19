package com.example.moviesinfo.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesinfo.R
import com.example.moviesinfo.data.models.Trailer

class TrailerAdapter : RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>() {

    class TrailerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val trailerTextView = itemView.findViewById<TextView>(R.id.trailerTextView)
    }

    interface OnTrailerClickListener {
        fun onTrailerClick(trailer: Trailer)
    }

    private var _trailers = listOf<Trailer>()
    var trailers: List<Trailer>
        get() = _trailers
        set(value) {
            _trailers = value
            notifyDataSetChanged()
        }

    private var onTrailerClickListener: OnTrailerClickListener? = null

    fun setOnTrailerClickListener(listener: OnTrailerClickListener) {
        this.onTrailerClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.trailer_item,
            parent,
            false
        )
        return TrailerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return _trailers.size
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        val trailer = _trailers[position]

        holder.trailerTextView.text = trailer.name
        holder.itemView.setOnClickListener {
            onTrailerClickListener?.onTrailerClick(trailer)
        }
    }

}