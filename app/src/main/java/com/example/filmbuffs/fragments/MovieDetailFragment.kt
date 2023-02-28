package com.example.filmbuffs.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmbuffs.R
import com.example.filmbuffs.adapters.MovieCastAdapter
import com.example.filmbuffs.viewmodels.MovieDetailViewModel
import com.squareup.picasso.Picasso


class MovieDetailFragment : Fragment() {
    private val TAG = "Detail Fragment"
    //ViewModel object
    private val viewModel: MovieDetailViewModel by viewModels()
    //Fields
    private var moviedescription: TextView? = null
    private var movieposter: ImageView? = null
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: MovieCastAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_moviedetails,container,false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        //(requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        //(requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //(requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        //(requireActivity() as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        moviedescription = view.findViewById(R.id.moviecontentstxt)
        movieposter = view.findViewById(R.id.moviebanner)
        recyclerview = view.findViewById(R.id.cast_list)
        recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        adapter = MovieCastAdapter()
        recyclerview.adapter = adapter
        val movieId = requireArguments().getInt("movie_id").toString()
        viewModel.getMoviebyId(movieId)
        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            moviedescription!!.text = movie.overview
            Picasso.with(activity)
                .load("https://image.tmdb.org/t/p/original" + movie.posterPath)
                .placeholder(R.drawable.ic_action_placeholder)
                .error(R.drawable.ic_action_error_placeholder)
                .into(movieposter)
        }
        viewModel.getCastbyId(movieId)
        viewModel.cast.observe(viewLifecycleOwner,{ cast -> adapter.updateMovies(cast) })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }
}