package com.example.filmbuffs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.navigation.NavController
import com.example.filmbuffs.adapters.MovieAdapter
import com.example.filmbuffs.databinding.FragmentMainBinding

class MainActivity : AppCompatActivity() {

    //
    private lateinit var navController: NavController

    private lateinit var adapter: MovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      //val navHostFragment = supportFragmentManager
        //findFragmentById(R.id.fragment) as NavHostFragment
      //navController = navHostFragment.navController


    }
}