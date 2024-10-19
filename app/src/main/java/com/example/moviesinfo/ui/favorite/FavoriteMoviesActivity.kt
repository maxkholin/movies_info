package com.example.moviesinfo.ui.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesinfo.R
import com.example.moviesinfo.data.models.Movie
import com.example.moviesinfo.ui.detail.MovieDetailActivity
import com.example.moviesinfo.ui.main.MoviesAdapter

class FavoriteMoviesActivity : AppCompatActivity() {
    companion object {
        fun newIntent(context: Context) = Intent(context, FavoriteMoviesActivity::class.java)
    }

    private lateinit var recyclerViewMovies: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var viewModel: FavoriteMoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favorite_movies)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initComponents()
        setupRecyclerView()
        observeViewModel()
        setupAdapter()
    }

    private fun initComponents() {
        recyclerViewMovies = findViewById(R.id.recyclerViewMovies)
        moviesAdapter = MoviesAdapter()
        viewModel = ViewModelProvider(this)[FavoriteMoviesViewModel::class.java]
    }

    private fun setupRecyclerView() {
        recyclerViewMovies.adapter = moviesAdapter
        recyclerViewMovies.layoutManager = GridLayoutManager(this, 2)
    }

    private fun observeViewModel() {
        viewModel.movies.observe(this) { movies ->
            moviesAdapter.movies = movies
        }
    }

    private fun setupAdapter() {
        moviesAdapter.setOnMovieClickListener(object : MoviesAdapter.OnMovieClickListener {
            override fun onMovieClick(movie: Movie) {
                startActivity(
                    MovieDetailActivity.newIntent(
                        this@FavoriteMoviesActivity,
                        movie
                    )
                )
            }
        })
    }

}