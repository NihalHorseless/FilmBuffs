package com.example.filmbuffs.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmbuffs.data.remote.models.popularmoviemodel.Movie
import com.example.filmbuffs.di.networkcalls.NetworkModule
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val TAG: String = "MainViewModel"

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    //Retrofit instance
    private val apiService = NetworkModule.moviesApi

    fun getMovies() {
        viewModelScope.launch {
            try {
                val response = apiService.getMovies()
                if (response.isSuccessful) {
                    val movies = response.body()!!.movies
                    _movies.postValue(movies)
                }

            } catch (e: Exception) {
                Log.d(TAG, "Can't connect!")
            }
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                val response = apiService.searchMovie(query)
                if (response.isSuccessful) {
                    val movies = response.body()!!.movies
                    _movies.postValue(movies)
                }

            } catch (e: Exception) {
                Log.d(TAG, "Can't connect!")
            }
        }
    }
}