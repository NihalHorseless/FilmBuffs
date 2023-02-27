package com.example.filmbuffs.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.filmbuffs.R
import com.example.filmbuffs.databinding.FragmentMoviedetailsBinding
import com.example.filmbuffs.viewmodels.MainViewModel
import com.example.filmbuffs.viewmodels.MovieDetailViewModel
import com.squareup.picasso.Picasso


class MovieDetailFragment : Fragment() {
    // Initializing View binding object with get method because it's null safe that way
    //private var _binding: FragmentMoviedetailsBinding? = null
    //private val binding get() = _binding!!
    private val TAG = "Detail Fragment"
    //ViewModel object
    private val viewModel: MovieDetailViewModel by viewModels()
    //Fields
    private var moviedescription: TextView? = null
    private var movieposter: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_moviedetails,container,false)
        //binding.moviecontentstxt.text = descp
        Log.d(TAG,"Entered view")
        return view
        Log.d(TAG,"Out of the view")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviedescription = view.findViewById(R.id.moviecontentstxt)
        movieposter = view.findViewById(R.id.moviebanner)
        val movieId = requireArguments().getInt("movie_id")
        viewModel.getMovie(movieId)
        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            moviedescription!!.text = movie.overview
            Picasso.with(activity)
                .load("https://image.tmdb.org/t/p/original" + movie.posterPath)
                .placeholder(R.drawable.ic_action_placeholder)
                .error(R.drawable.ic_action_error_placeholder)
                .into(movieposter)
        }
    }
}