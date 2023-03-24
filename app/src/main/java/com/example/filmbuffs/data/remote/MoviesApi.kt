package com.example.filmbuffs.data.remote
import com.example.filmbuffs.data.remote.models.castmodel.CastList
import com.example.filmbuffs.data.remote.models.popularmoviemodel.TotalResults
import com.example.filmbuffs.data.remote.models.singlemoviemodel.SingleMovieDetail
import com.example.filmbuffs.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
     // Get popular Movies
     @GET("movie/now_playing?api_key=$API_KEY&language=en-US&page=1")
     suspend fun getMovies(): Response<TotalResults>
     // Get movie by id
     @GET("movie/{movie_id}?api_key=$API_KEY")
     suspend fun getMovie(@Path("movie_id") movieId:String): Response<SingleMovieDetail>
     // Get actors by movie id
     @GET("movie/{movie_id}/credits?api_key=$API_KEY")
     suspend fun getCast(@Path("movie_id") movieId: String) : Response<CastList>
     // Get Searched Movies
     @GET("search/movie?api_key=${API_KEY}")
     suspend fun searchMovie(@Query("query") movieName: String) : Response<TotalResults>
}