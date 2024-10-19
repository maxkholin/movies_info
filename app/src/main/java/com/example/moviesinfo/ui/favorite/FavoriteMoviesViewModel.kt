package com.example.moviesinfo.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.moviesinfo.data.database.MovieDatabase
import com.example.moviesinfo.data.models.Movie

class FavoriteMoviesViewModel(application: Application) : AndroidViewModel(application) {
    private val movieDao = MovieDatabase.getInstance(application).movieDao()
    val movies: LiveData<List<Movie>> = movieDao.getAllFavoriteMovies()


}