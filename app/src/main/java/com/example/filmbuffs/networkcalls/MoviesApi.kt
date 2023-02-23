package com.example.filmbuffs.networkcalls
import com.example.filmbuffs.models.popularmoviemodel.Movie
import retrofit2.Call
import retrofit2.http.GET
import com.example.filmbuffs.models.popularmoviemodel.TotalResults
import com.example.filmbuffs.models.singlemoviemodel.SingleMovieDetail
import com.example.filmbuffs.util.Constants.Companion.API_KEY
import retrofit2.http.Path

interface MoviesApi {
    // Get popular Movies
    @GET("movie/now_playing?api_key=$API_KEY&language=en-US&page=1")
    fun getMovies(): Call<TotalResults>
    // Get movie by id
    @GET("movie/{id}?api_key=$API_KEY&language=en-US")
     fun getMovie(@Path(value = "id") itemId:Int): Call<SingleMovieDetail>
}