package com.example.filmbuffs.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmbuffs.models.popularmoviemodel.Movie
import com.example.filmbuffs.models.popularmoviemodel.TotalResults
import com.example.filmbuffs.models.topratedmovies.TopRatedMovies
import com.example.filmbuffs.networkcalls.MoviesApi
import com.example.filmbuffs.networkcalls.NetworkModule
import retrofit2.*

class MainViewModel : ViewModel() {
    private val TAG : String = "MainViewModel"
    private val apiService = NetworkModule().getRetrofitInstance().create(MoviesApi::class.java)
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
    get() = _movies
    private val _topmovies = MutableLiveData<List<com.example.filmbuffs.models.topratedmovies.Result>>()
    val topmovies: LiveData<List<com.example.filmbuffs.models.topratedmovies.Result>>
        get() = _topmovies
    fun getMovies() {
        val call = apiService.getMovies()
        call.enqueue(object: Callback<TotalResults> {
            override fun onFailure(call: Call<TotalResults>, t: Throwable) {
                Log.d(TAG,t.message!!)
            }
            override fun onResponse(call: Call<TotalResults>, response: Response<TotalResults>) {
                if(response.isSuccessful) {
                    Log.d(TAG,"Success!")
                    val movies = response.body()!!.movies
                    _movies.postValue(movies)
                }
            }
        })
    }
    fun getTopMovies() {
        val call = apiService.getTopMovies()
        call.enqueue(object: Callback<TopRatedMovies> {
            override fun onFailure(call: Call<TopRatedMovies>, t: Throwable) {
                Log.d(TAG,t.message!!)
            }
            override fun onResponse(call: Call<TopRatedMovies>, response: Response<TopRatedMovies>) {
                if(response.isSuccessful) {
                    Log.d(TAG,"Success!")
                    val topmovies = response.body()!!.results
                    _topmovies.postValue(topmovies)
                }
            }
        })

    }
}