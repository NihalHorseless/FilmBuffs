package com.example.filmbuffs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmbuffs.adapters.MovieAdapter
import com.example.filmbuffs.databinding.ActivityMainBinding
import com.example.filmbuffs.networkcalls.MoviesApi
import com.example.filmbuffs.objects.Movies
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(MoviesApi::class.java)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        api.getMovies().enqueue(object: Callback<Movies>{
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Log.d("Not Working",t.message!!)
            }

            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                binding.movielist.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
                val adapter = MovieAdapter(response.body()!!.results,this@MainActivity)
                binding.movielist.adapter = adapter
            }
        })
    }
}