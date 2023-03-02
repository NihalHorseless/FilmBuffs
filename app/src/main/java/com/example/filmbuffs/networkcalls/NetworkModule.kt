package com.example.filmbuffs.networkcalls



import com.example.filmbuffs.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    //Setting up the Retrofit object
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val moviesApi: MoviesApi = retrofit.create(MoviesApi::class.java)

    fun getRetrofitInstance(): Retrofit {
        return retrofit
    }


}