package com.example.filmbuffs.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.filmbuffs.R
import com.example.filmbuffs.databinding.FragmentMoviedetailsBinding
import com.example.filmbuffs.viewmodels.MainViewModel
import com.example.filmbuffs.viewmodels.MovieDetailViewModel
import com.squareup.picasso.Picasso


class MovieDetailFragment : Fragment() {
    // Initializing View binding object with get method because it's null safe that way
    private var _binding: FragmentMoviedetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoviedetailsBinding.inflate(inflater,container,false)
        val view = binding.root
        val descp = arguments?.getString("description_key")
        val url = arguments?.getString("image_url")
        Picasso.with(activity)
            .load("https://image.tmdb.org/t/p/original"+url)
            .placeholder(R.drawable.ic_action_placeholder)
            .error(R.drawable.ic_action_error_placeholder)
            .into(binding.moviebanner)
        binding.moviecontentstxt.text = descp
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}