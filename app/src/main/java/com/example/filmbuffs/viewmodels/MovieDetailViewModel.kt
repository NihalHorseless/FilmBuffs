package com.example.filmbuffs.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmbuffs.models.castmodel.Cast
import com.example.filmbuffs.models.castmodel.CastList
import com.example.filmbuffs.models.singlemoviemodel.SingleMovieDetail
import com.example.filmbuffs.networkcalls.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailViewModel: ViewModel() {
    private val TAG : String = "MovieDetailViewModel"
    private val _movie = MutableLiveData<SingleMovieDetail>()
    val movie: LiveData<SingleMovieDetail>
        get() = _movie
    private val _cast = MutableLiveData<List<Cast>>()
    val cast: LiveData<List<Cast>>
        get() = _cast
    //Retrofit instance
    private val apiService = NetworkModule.moviesApi

    fun getMovieById(movieId: String) {
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
    fun getCastById(movieId: String){
        val call = apiService.getCast(movieId)
        call.enqueue(object: Callback<CastList> {
            override fun onFailure(call: Call<CastList>, t: Throwable) {
                Log.d(TAG,t.message!!)
            }
            override fun onResponse(call: Call<CastList>, response: Response<CastList>) {
                if(response.isSuccessful) {
                    Log.d(TAG,"Success!")
                    val cast = response.body()!!.cast
                    _cast.postValue(cast)
                }
            }
        })
    }
}