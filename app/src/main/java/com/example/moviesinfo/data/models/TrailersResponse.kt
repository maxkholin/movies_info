package com.example.moviesinfo.data.models

import com.google.gson.annotations.SerializedName

data class TrailersResponse(
    @SerializedName("videos")
    val trailersList: TrailersList
)

data class TrailersList(
    @SerializedName("trailers")
    val trailers: List<Trailer>
)

data class Trailer(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
