package com.example.filmbuffs.networkcalls
import retrofit2.Call
import retrofit2.http.GET
import com.example.filmbuffs.objects.Movies
interface MoviesApi {
    @GET("movie/now_playing?api_key=a226c1f097dc804afc9b87c9e4111be3&language=en-US&page=1")
    fun getMovies(): Call<Movies>
}