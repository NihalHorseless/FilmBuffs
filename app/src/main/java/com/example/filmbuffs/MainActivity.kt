package com.example.filmbuffs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmbuffs.networkcalls.NetworkModule.api
import com.example.filmbuffs.adapters.MovieAdapter
import com.example.filmbuffs.databinding.FragmentMainBinding
import com.example.filmbuffs.models.popularmoviemodel.Movies
import retrofit2.*

class MainActivity : AppCompatActivity() {
    // View Binding Object
    private lateinit var binding: FragmentMainBinding
    //
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
      //val navHostFragment = supportFragmentManager
        // findFragmentById(R.id.fragment) as NavHostFragment
      //navController = navHostFragment.navController

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