package com.example.moviesinfo.data.models

import com.google.gson.annotations.SerializedName

data class ReviewsResponse(
    @SerializedName("docs")
    val reviews: List<Review>
)

data class Review(
    @SerializedName("author")
    val author: String,
    @SerializedName("review")
    val review: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("movieId")
    val movieId: Int
)