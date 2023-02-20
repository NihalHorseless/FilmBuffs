package com.example.filmbuffs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmbuffs.adapters.MovieAdapter
import com.example.filmbuffs.databinding.ActivityMainBinding
import com.example.filmbuffs.databinding.FragmentMainBinding
import com.example.filmbuffs.networkcalls.MoviesApi
import com.example.filmbuffs.objects.Movies
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    //Setting up the Retrofit object
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(MoviesApi::class.java)
    // View Binding Object
    private lateinit var binding: FragmentMainBinding
    //
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMainBinding.inflate(layoutInflater)
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