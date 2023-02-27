package com.example.filmbuffs.networkcalls

import com.example.filmbuffs.models.popularmoviemodel.TotalResults
import com.example.filmbuffs.models.singlemoviemodel.SingleMovieDetail
import com.example.filmbuffs.util.Constants.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule {
    //Setting up the Retrofit object
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(MoviesApi::class.java)
    fun getRetrofitInstance() : Retrofit {
        return retrofit
    }

    fun getMovies(): Call<TotalResults> {
        return api.getMovies()
    }
     fun getMovie(movieId: String): Call<SingleMovieDetail> {
        return api.getMovie(movieId)
    }
}