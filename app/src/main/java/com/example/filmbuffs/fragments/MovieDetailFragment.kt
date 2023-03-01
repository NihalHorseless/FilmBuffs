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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmbuffs.R
import com.example.filmbuffs.adapters.MovieCastAdapter
import com.example.filmbuffs.viewmodels.MovieDetailViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso


class MovieDetailFragment : Fragment() {
    private val TAG = "Detail Fragment"
    //ViewModel object
    private val viewModel: MovieDetailViewModel by viewModels()
    //Fields
    private lateinit var navController: NavController
    private var moviedescription: TextView? = null
    private var movieposter: ImageView? = null
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: MovieCastAdapter
    private lateinit var backbutton: FloatingActionButton

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
        initializeView(view)

    }
    fun initializeView(view:View){
        backbutton = view.findViewById(R.id.backbutton)
        moviedescription = view.findViewById(R.id.moviecontentstxt)
        movieposter = view.findViewById(R.id.moviebanner)
        recyclerview = view.findViewById(R.id.cast_list)
        backbutton.setOnClickListener { findNavController().navigate(R.id.action_movieDetailFragment_to_mainFragment) }
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


}
