package com.example.filmbuffs.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmbuffs.R
import com.example.filmbuffs.adapters.MovieCastAdapter
import com.example.filmbuffs.databinding.FragmentMoviedetailsBinding
import com.example.filmbuffs.viewmodels.MovieDetailViewModel
import com.squareup.picasso.Picasso


class MovieDetailFragment : Fragment() {
    private val TAG = "Detail Fragment"

    //ViewModel object
    private val viewModel: MovieDetailViewModel by viewModels()

    //Fields
    private var movieDescription: TextView? = null
    private var moviePoster: ImageView? = null
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: MovieCastAdapter
    private lateinit var binding: FragmentMoviedetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "OPEN")
        // Inflate the layout for this fragment
        binding = FragmentMoviedetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()

    }

    private fun initializeView() {
        movieDescription = binding.moviecontentstxt
        moviePoster = binding.moviebanner
        recyclerview = binding.castList
        recyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = MovieCastAdapter()
        recyclerview.adapter = adapter
        val movieId = requireArguments().getInt("movie_id").toString()
        viewModel.getMovieById(movieId)
        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            movieDescription!!.text = movie.overview
            Picasso.with(activity)
                .load("https://image.tmdb.org/t/p/original" + movie.posterPath)
                .placeholder(R.drawable.ic_action_placeholder)
                .error(R.drawable.ic_action_error_placeholder)
                .into(moviePoster)
        }
        viewModel.getCastById(movieId)
        viewModel.cast.observe(viewLifecycleOwner) { cast -> adapter.updateMovies(cast) }
    }


}
