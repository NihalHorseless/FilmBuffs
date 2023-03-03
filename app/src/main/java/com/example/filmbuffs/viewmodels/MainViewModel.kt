package com.example.filmbuffs.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmbuffs.models.popularmoviemodel.Movie
import com.example.filmbuffs.models.popularmoviemodel.TotalResults
import com.example.filmbuffs.networkcalls.NetworkModule
import retrofit2.*

class MainViewModel : ViewModel() {
    private val TAG: String = "MainViewModel"
    private val apiService = NetworkModule.moviesApi
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    fun getMovies() {
        val call = apiService.getMovies()
        call.enqueue(object : Callback<TotalResults> {
            override fun onFailure(call: Call<TotalResults>, t: Throwable) {
                Log.d(TAG, t.message!!)
            }

            override fun onResponse(call: Call<TotalResults>, response: Response<TotalResults>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Success!")
                    val movies = response.body()!!.movies
                    _movies.postValue(movies)
                }
            }
        })
    }

    fun searchMovies(query: String) {
        val call = apiService.searchMovie(query)
        call.enqueue(object : Callback<TotalResults> {
            override fun onFailure(call: Call<TotalResults>, t: Throwable) {
                Log.d(TAG, t.message!!)
            }

            override fun onResponse(call: Call<TotalResults>, response: Response<TotalResults>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Success!")
                    val movies = response.body()!!.movies
                    _movies.postValue(movies)
                }
            }
        })

    }
}