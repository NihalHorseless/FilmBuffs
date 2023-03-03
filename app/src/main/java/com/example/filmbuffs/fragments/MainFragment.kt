package com.example.filmbuffs.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmbuffs.R
import com.example.filmbuffs.adapters.MovieAdapter
import com.example.filmbuffs.databinding.FragmentMainBinding
import com.example.filmbuffs.viewmodels.MainViewModel

class MainFragment : Fragment() {

    private val TAG = "Main Fragment"
    private lateinit var navController: NavController

    // View Model
    private val myViewModel: MainViewModel by viewModels()

    // Fields
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var binding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        initializeFields()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        myViewModel.movies.removeObservers(viewLifecycleOwner)
    }

    fun initializeFields() {
        recyclerView = binding.movielist
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        movieAdapter = MovieAdapter()
        recyclerView.adapter = movieAdapter

        myViewModel.getMovies()
        myViewModel.movies.observe(viewLifecycleOwner) { movies ->
            movieAdapter.updateMovies(
                movies
            )
        }
        movieAdapter.setOnItemClickListener(MovieAdapter.OnItemClickListener {
            Log.d(TAG, "Clicked on ${it.title}")
            val bundle = Bundle()
            bundle.putInt("movie_id", it.id)
            MovieDetailFragment().arguments = bundle
            findNavController().navigate(R.id.action_mainFragment_to_movieDetailFragment, bundle)
            Log.d(TAG, "Navigation Done!")
        })
    }

    fun search(query: String) {
        binding.popularmoviestxt.text = "Here are the Results for : $query"
        myViewModel.searchMovies(query)
        myViewModel.movies.observe(viewLifecycleOwner) { movies ->
            if (isAdded) {
                movieAdapter.updateMovies(movies)
            }

        }
    }

    fun showDefaultResults() {
        myViewModel.getMovies()
        myViewModel.movies.observe(viewLifecycleOwner) { movies ->
            movieAdapter.updateMovies(
                movies
            )
        }

    }


}