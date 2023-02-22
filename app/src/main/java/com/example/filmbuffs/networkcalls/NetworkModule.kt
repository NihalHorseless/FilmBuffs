package com.example.filmbuffs.networkcalls

import com.example.filmbuffs.models.popularmoviemodel.Movie
import com.example.filmbuffs.models.popularmoviemodel.TotalResults
import com.example.filmbuffs.models.singlemoviemodel.SingleMovieDetail
import com.example.filmbuffs.util.Constants.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule {
    //Setting up the Retrofit object
    val api: MoviesApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApi::class.java)
    }
    fun getMovies(): Call<List<Movie>> {
        return api.getMovies()
    }
     fun getMovie(id: Int): Call<SingleMovieDetail> {
        return api.getMovie(id)
    }
}