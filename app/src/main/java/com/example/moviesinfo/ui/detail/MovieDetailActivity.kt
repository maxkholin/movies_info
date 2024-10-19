package com.example.moviesinfo.ui.detail

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import com.example.moviesinfo.R
import com.example.moviesinfo.data.models.Movie
import com.example.moviesinfo.data.models.Trailer
import kotlinx.coroutines.launch

private const val EXTRA_MOVIE = "movie"

class MovieDetailActivity : AppCompatActivity() {
    companion object {
        fun newIntent(context: Context, movie: Movie): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_MOVIE, movie)

            return intent
        }
    }

    private lateinit var posterImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var yearTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var starImageView: ImageView
    private var starOff: Drawable? = null
    private var starOn: Drawable? = null

    private lateinit var recyclerViewTrailers: RecyclerView
    private lateinit var trailerAdapter: TrailerAdapter

    private lateinit var recyclerViewReviews: RecyclerView
    private lateinit var reviewAdapter: ReviewAdapter

    private lateinit var viewModel: MovieDetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initComponents()
        setupRecyclerViews()

        val movie = intent.getSerializableExtra(EXTRA_MOVIE) as Movie

        setViews(movie)
        loadData(movie.id)

        observeViewModel(movie)
        setupTrailerAdapter()

    }


    private fun initComponents() {
        posterImageView = findViewById(R.id.posterImageView)
        titleTextView = findViewById(R.id.titleTextView)
        yearTextView = findViewById(R.id.yearTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        starImageView = findViewById(R.id.starImageView)
        starOff = ContextCompat.getDrawable(this, android.R.drawable.star_big_off)
        starOn = ContextCompat.getDrawable(this, android.R.drawable.star_big_on)

        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers)
        trailerAdapter = TrailerAdapter()

        recyclerViewReviews = findViewById(R.id.recyclerViewReviews)
        reviewAdapter = ReviewAdapter()

        viewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]

    }

    private fun setupRecyclerViews() {
        recyclerViewTrailers.adapter = trailerAdapter
        recyclerViewReviews.adapter = reviewAdapter
    }

    private fun setViews(movie: Movie) {
        movie.let {
            posterImageView.load(it.poster.url) {
                crossfade(true)
                error(R.drawable.error_image)
            }
            titleTextView.text = it.name ?: it.alternativeName
            yearTextView.text = it.year.toString()
            descriptionTextView.text = it.description
        }
    }

    private fun loadData(movieId: Int) {
        loadTrailers(movieId)
        loadReviews(movieId)
    }

    private fun loadTrailers(movieId: Int) {
        lifecycleScope.launch {
            viewModel.loadTrailers(movieId)
        }
    }

    private fun loadReviews(movieId: Int) {
        lifecycleScope.launch {
            viewModel.loadReviews(movieId)
        }
    }

    private fun observeViewModel(movie: Movie) {
        viewModel.trailers.observe(this) { trailers ->
            trailerAdapter.trailers = trailers
        }

        viewModel.reviews.observe(this) { reviews ->
            reviewAdapter.reviews = reviews
        }

        viewModel.getFavoriteMovie(movie.id).observe(this) { movieFromDatabase ->
            if (movieFromDatabase == null) {
                starImageView.setImageDrawable(starOff)
                starImageView.setOnClickListener {
                    viewModel.insertMovie(movie)
                }
            } else {
                starImageView.setImageDrawable(starOn)
                starImageView.setOnClickListener {
                    viewModel.removeMovie(movie.id)
                }
            }
        }
    }

    private fun setupTrailerAdapter() {
        trailerAdapter.setOnTrailerClickListener(object : TrailerAdapter.OnTrailerClickListener {
            override fun onTrailerClick(trailer: Trailer) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(trailer.url)
                startActivity(intent)
            }
        })
    }
}