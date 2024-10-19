package com.example.moviesinfo.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moviesinfo.data.models.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM favorite_movies")
    fun getAllFavoriteMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM favorite_movies WHERE id = :movieId")
    fun getOneFavoriteMovie(movieId: Int): LiveData<Movie>

    @Insert
    suspend fun insertMovieToFavorite(movie: Movie)

    @Query("DELETE  FROM favorite_movies WHERE id = :movieId")
    suspend fun removeMovieFromFavorite(movieId: Int)
}