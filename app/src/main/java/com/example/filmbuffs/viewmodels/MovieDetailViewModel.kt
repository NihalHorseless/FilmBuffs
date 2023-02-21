package com.example.filmbuffs.viewmodels

import androidx.lifecycle.ViewModel
import com.example.filmbuffs.databinding.FragmentMoviedetailsBinding
import com.example.filmbuffs.util.Constants.Companion.BASE_URL

class MovieDetailViewModel: ViewModel() {
    //Variables
    private val _moviedescription = "In a Galaxy Far Away"
    val moviedescription: String
        get() = _moviedescription
    private var _imageurl = BASE_URL
    val imageurl: String
        get() = _imageurl
    private var _movieId = 0
    val movieId: Int
        get() = _movieId
    //Binding object
    private var _binding: FragmentMoviedetailsBinding? = null
    private val binding get() = _binding!!

    fun displayMovie() {

    }
}