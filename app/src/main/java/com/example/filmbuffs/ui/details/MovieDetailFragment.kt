package com.example.filmbuffs.ui.details

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmbuffs.R
import com.example.filmbuffs.data.local.database.LocalMovie
import com.example.filmbuffs.data.repository.MovieRepository
import com.example.filmbuffs.databinding.FragmentMoviedetailsBinding
import com.example.filmbuffs.ui.adapters.MovieCastAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso


class MovieDetailFragment : Fragment() {
    // private val TAG = "DetailFragment"

    //ViewModel object
    private lateinit var viewModel: MovieDetailViewModel

    //Fields
    private var movieDescription: TextView? = null
    private var movieName: TextView? = null
    private var movieReleaseDate: TextView? = null
    private var movieRating: TextView? = null
    private var moviePoster: ImageView? = null
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: MovieCastAdapter
    private lateinit var favoriteButton: FloatingActionButton

    //Binding Object
    private lateinit var binding: FragmentMoviedetailsBinding

    //Args
    private val args by navArgs<MovieDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMoviedetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()

    }

    private fun initializeView() {
        viewModel = ViewModelProvider(
            this, MovieDetailViewModel.MovieDetailViewModelFactory(
                MovieRepository(requireContext())
            )
        )[MovieDetailViewModel::class.java]

        movieDescription = binding.moviecontentstxt
        moviePoster = binding.moviebanner
        movieName = binding.movieName
        movieReleaseDate = binding.movieReleaseDate
        movieRating = binding.movieRating
        favoriteButton = binding.fab

        recyclerview = binding.castList
        recyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        adapter = MovieCastAdapter()
        recyclerview.adapter = adapter

        val movieId = args.movieId
        viewModel.getMovieById(movieId.toString())
        // Release Date, Genres,Vote average
        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            movieDescription!!.text = movie.overview
            movieName!!.text = movie.title
            movieRating!!.text =
                getString(R.string.movie_rating) + String.format("%.2f", movie.voteAverage) + "/10"
            movieReleaseDate!!.text = getString(R.string.movie_release_date) + movie.releaseDate
            Picasso.with(activity)
                .load("https://image.tmdb.org/t/p/original" + movie.posterPath)
                .placeholder(R.drawable.ic_action_placeholder)
                .error(R.drawable.ic_action_error_placeholder)
                .into(moviePoster)
        }

        viewModel.getCastById(movieId.toString())
        viewModel.cast.observe(viewLifecycleOwner) { cast -> adapter.updateMovies(cast) }

        viewModel.checkMovieLiked(movieId)
        viewModel.isMovieLiked.observe(viewLifecycleOwner) { isLiked ->
            if (isLiked) {
                // If the movie is added to favorites,the button's icon and background color changes
                favoriteButton.setImageResource(R.drawable.ic_remove_like)
                favoriteButton.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.text_icons))
            } else {
                // If the movie is not liked, set the button's icon and background color to indicate that it is not liked
                favoriteButton.setImageResource(R.drawable.ic_like)
                favoriteButton.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.light_primary))
            }
        }
        favoriteButton.setOnClickListener {
            if (viewModel.isMovieLiked.value == false) {
                viewModel.addMovie(
                    LocalMovie(
                        viewModel.movie.value!!.id,
                        viewModel.movie.value!!.originalTitle,
                        viewModel.movie.value!!.posterPath
                    )
                )
                Toast.makeText(activity, "Added to the Favorites!", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.delete(
                    LocalMovie(
                        viewModel.movie.value!!.id,
                        viewModel.movie.value!!.originalTitle,
                        viewModel.movie.value!!.posterPath
                    )
                )
                Toast.makeText(activity, "Removed From Favorites!", Toast.LENGTH_SHORT).show()
            }
            viewModel.checkMovieLiked(movieId)
        }

    }


}
