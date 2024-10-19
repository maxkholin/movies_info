package com.example.moviesinfo.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
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
import com.example.moviesinfo.ui.favorite.FavoriteMoviesActivity

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerViewMovies: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter

    private lateinit var progressBarLoading: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initComponents()
        setupRecyclerView()
        setupAdapter()
        observeViewModel()
        loadMovies()
    }

    private fun initComponents() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        recyclerViewMovies = findViewById(R.id.recyclerViewMovies)
        moviesAdapter = MoviesAdapter()

        progressBarLoading = findViewById(R.id.progressBarLoading)
    }

    private fun setupRecyclerView() {
        recyclerViewMovies.adapter = moviesAdapter
        recyclerViewMovies.layoutManager = GridLayoutManager(this, 2)
    }

    private fun setupAdapter() {
        moviesAdapter.setOnLoadMoreListener(object : MoviesAdapter.OnLoadMoreListener {
            override fun onLoadMore() {
                loadMovies()
            }
        })

        moviesAdapter.setOnMovieClickListener(object : MoviesAdapter.OnMovieClickListener {
            override fun onMovieClick(movie: Movie) {
                val intent = MovieDetailActivity.newIntent(this@MainActivity, movie)
                startActivity(intent)
            }
        })
    }

    private fun observeViewModel() {
        viewModel.movies.observe(this) { movies ->
            moviesAdapter.movies = movies
        }

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                progressBarLoading.visibility = View.VISIBLE
            } else {
                progressBarLoading.visibility = View.GONE
            }
        }
    }

    private fun loadMovies() {
        viewModel.loadMovies()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorite_movies) {
            startActivity(FavoriteMoviesActivity.newIntent(this))
        }
        return super.onOptionsItemSelected(item)
    }
}