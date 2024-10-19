package com.example.moviesinfo.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MoviesResponse(
    @SerializedName("docs")
    val movies: List<Movie>
)

@Entity(tableName = "favorite_movies")
data class Movie(

    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("year")
    val year: Int,
    @SerializedName("description")
    val description: String,
    @Embedded
    @SerializedName("rating")
    val rating: Rating,
    @Embedded
    @SerializedName("poster")
    val poster: Poster,
    @SerializedName("alternativeName")
    val alternativeName: String? = name
) : Serializable

data class Rating(
    @SerializedName("kp")
    val rating: Double
) : Serializable

data class Poster(
    @SerializedName("url")
    val url: String?
) : Serializable
