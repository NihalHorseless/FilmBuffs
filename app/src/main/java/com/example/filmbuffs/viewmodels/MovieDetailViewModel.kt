package com.example.filmbuffs.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmbuffs.models.singlemoviemodel.SingleMovieDetail
import com.example.filmbuffs.networkcalls.MoviesApi
import com.example.filmbuffs.networkcalls.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailViewModel: ViewModel() {
    private val TAG : String = "MovieDetailViewModel"
    private val _movie = MutableLiveData<SingleMovieDetail>()
    val movie: LiveData<SingleMovieDetail>
        get() = _movie
    //Retrofit instance
    private val apiService = NetworkModule().getRetrofitInstance().create(MoviesApi::class.java)

    fun getMoviebyId(movieId: String) {
        val call = apiService.getMovie(movieId)
        call.enqueue(object: Callback<SingleMovieDetail> {
            override fun onFailure(call: Call<SingleMovieDetail>, t: Throwable) {
                Log.d(TAG,"Failed!")
            }

            override fun onResponse(call: Call<SingleMovieDetail>, response: Response<SingleMovieDetail>) {
                if(response.isSuccessful) {
                    Log.d(TAG,"Success!")
                    val movie = response.body()!!
                    _movie.postValue(movie)

                }
            }
        })


    }
}