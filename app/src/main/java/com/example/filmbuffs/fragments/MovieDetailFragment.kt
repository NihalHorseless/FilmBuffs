package com.example.filmbuffs.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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
    private var searchMenuItem: MenuItem? = null
    private var movieDescription: TextView? = null
    private var moviePoster: ImageView? = null
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: MovieCastAdapter
    private lateinit var binding: FragmentMoviedetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "OPEN")
        // Inflate the layout for this fragment
        binding = FragmentMoviedetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        searchMenuItem = menu.findItem(R.id.action_search)
        searchMenuItem?.isVisible = false
    }

    override fun onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu()
        searchMenuItem?.isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
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
