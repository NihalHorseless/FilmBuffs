package com.example.filmbuffs.networkcalls
import com.example.filmbuffs.models.castmodel.CastList
import retrofit2.Call
import retrofit2.http.GET
import com.example.filmbuffs.models.popularmoviemodel.TotalResults
import com.example.filmbuffs.models.singlemoviemodel.SingleMovieDetail
import com.example.filmbuffs.models.topratedmovies.TopRatedMovies
import com.example.filmbuffs.util.Constants.Companion.API_KEY
import retrofit2.http.Path

interface MoviesApi {
     // Get popular Movies
     @GET("movie/now_playing?api_key=$API_KEY&language=en-US&page=1")
     fun getMovies(): Call<TotalResults>
     // Get movie by id
     @GET("movie/{movie_id}?api_key=$API_KEY")
     fun getMovie(@Path("movie_id") movieId:String): Call<SingleMovieDetail>
     // Get actors by movie id
     @GET("movie/{movie_id}/credits?api_key=$API_KEY")
     fun getCast(@Path("movie_id") movieId: String) : Call<CastList>
     // Get top rated Movies
     @GET("movie/top_rated?api_key=$API_KEY")
     fun getTopMovies(): Call<TopRatedMovies>
}