package com.example.filmbuffs.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmbuffs.databinding.FragmentMainBinding
import com.example.filmbuffs.models.popularmoviemodel.Movie
import com.example.filmbuffs.models.popularmoviemodel.TotalResults
import com.example.filmbuffs.networkcalls.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private lateinit var binding: FragmentMainBinding

    private val apiService = NetworkModule()

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
    get() = _movies
    fun getMovies() {

        apiService.getMovies().enqueue(object: Callback<List<Movie>> {
            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                Log.d("Not Working",t.message!!)
            }

            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                _movies.value = response.body()
            }
        })
    }
}