package com.example.moviesinfo.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviesinfo.data.api.RetrofitClient
import com.example.moviesinfo.data.models.Movie
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private var _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private var currentPage = 0

    fun loadMovies() {
        if (_isLoading.value == true) {
            return
        }
        _isLoading.value = true

        viewModelScope.launch {
            try {
                currentPage++

                val movieResponse = RetrofitClient.instance.loadMovies(page = currentPage)
                _movies.postValue(_movies.value.orEmpty() + movieResponse.movies)
            } catch (e: Exception) {
                Log.d(TAG, e.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }
}
