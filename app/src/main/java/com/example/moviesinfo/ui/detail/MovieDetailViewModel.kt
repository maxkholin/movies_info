package com.example.moviesinfo.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviesinfo.data.api.RetrofitClient
import com.example.moviesinfo.data.database.MovieDatabase
import com.example.moviesinfo.data.models.Movie
import com.example.moviesinfo.data.models.Review
import com.example.moviesinfo.data.models.Trailer
import kotlinx.coroutines.launch

private const val TAG = "MovieDetailViewModel"

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val _trailers = MutableLiveData<List<Trailer>>()
    val trailers: LiveData<List<Trailer>> = _trailers

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> = _reviews

    private var movieDao = MovieDatabase.getInstance(application).movieDao()

    fun getFavoriteMovie(movieId: Int): LiveData<Movie> {
        return movieDao.getOneFavoriteMovie(movieId)
    }

    fun insertMovie(movie: Movie) {
        viewModelScope.launch {
            try {
                movieDao.insertMovieToFavorite(movie)
            } catch (e: Exception) {
                Log.d(TAG, e.toString())
            }
        }
    }

    fun removeMovie(movieId: Int) {
        viewModelScope.launch {
            try {
                movieDao.removeMovieFromFavorite(movieId)
            } catch (e: Exception) {
                Log.d(TAG, e.toString())
            }
        }

    }

    fun loadTrailers(movieId: Int) {
        viewModelScope.launch {
            try {
                val trailersResponse = RetrofitClient.instance.loadTrailers(movieId)
                val trailersFromApi = trailersResponse.trailersList.trailers.toSet().toList()
                _trailers.postValue(trailersFromApi)
            } catch (e: Exception) {
                Log.d(TAG, e.toString())
            }
        }
    }

    fun loadReviews(movieId: Int) {
        viewModelScope.launch {
            try {
                val reviewsResponse = RetrofitClient.instance.loadReviews(movieId = movieId)
                val reviewsFromApi = reviewsResponse.reviews
                _reviews.postValue(reviewsFromApi)
            } catch (e: Exception) {
                Log.d(TAG, e.toString())
            }
        }
    }
}