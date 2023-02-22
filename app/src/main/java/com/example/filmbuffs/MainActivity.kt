package com.example.filmbuffs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.navigation.NavController
import com.example.filmbuffs.adapters.MovieAdapter
import com.example.filmbuffs.databinding.FragmentMainBinding

class MainActivity : AppCompatActivity() {
    // View Binding Object
    private lateinit var binding: FragmentMainBinding
    //
    private lateinit var navController: NavController

    private lateinit var adapter: MovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
      //val navHostFragment = supportFragmentManager
        //findFragmentById(R.id.fragment) as NavHostFragment
      //navController = navHostFragment.navController


    }
}