package com.example.filmbuffs.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmbuffs.R
import com.example.filmbuffs.adapters.MovieAdapter
import com.example.filmbuffs.viewmodels.MainViewModel

class MainFragment : Fragment() {


    private lateinit var navController: NavController
    //
    private val myViewModel: MainViewModel by viewModels()
    //
    private var recyclerview: RecyclerView? = null
    private var adapter: MovieAdapter? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main,container,false)
        recyclerview = view.findViewById(R.id.movielist)
        adapter = MovieAdapter(myViewModel.movies.value!!,requireActivity())
        recyclerview?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        recyclerview?.adapter = adapter

        myViewModel.getMovies()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myViewModel.movies.observe(viewLifecycleOwner,{ movies -> adapter?.updateMovies(movies) })

    }

}