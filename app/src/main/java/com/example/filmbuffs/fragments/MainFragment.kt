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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmbuffs.R
import com.example.filmbuffs.adapters.MovieAdapter
import com.example.filmbuffs.adapters.TopMovieAdapter
import com.example.filmbuffs.viewmodels.MainViewModel

class MainFragment : Fragment() {

    private val TAG = "Main Fragment"
    private lateinit var navController: NavController
    // View Model
    private val myViewModel: MainViewModel by viewModels()
    // Fields
    private lateinit var popular_recyclerview: RecyclerView
    private lateinit var popular_adapter: MovieAdapter
    private lateinit var top_recyclerview: RecyclerView
    private lateinit var top_adapter: TopMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main,container,false)
        InitializeFields(view)

        return view
    }
    fun InitializeFields(view: View){
        popular_recyclerview = view.findViewById(R.id.movielist)
        popular_recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        popular_adapter = MovieAdapter()
        popular_recyclerview.adapter = popular_adapter

        top_recyclerview = view.findViewById(R.id.topmovielist)
        top_recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        top_adapter = TopMovieAdapter()
        top_recyclerview.adapter = top_adapter

        myViewModel.getMovies()
        myViewModel.movies.observe(viewLifecycleOwner,{ movies -> popular_adapter.updateMovies(movies) })
        popular_adapter.setOnItemClickListener(MovieAdapter.OnItemClickListener {
            Log.d(TAG, "Clicked on ${it.title}")
            val bundle = Bundle()
            bundle.putInt("movie_id", it.id)
            MovieDetailFragment().arguments = bundle
            findNavController().navigate(R.id.action_mainFragment_to_movieDetailFragment, bundle)
        }
        )
        myViewModel.getTopMovies()
        myViewModel.topmovies.observe(viewLifecycleOwner,{ topmovies -> top_adapter.updateMovies(topmovies) })
        top_adapter.setOnItemClickListener(TopMovieAdapter.OnItemClickListener {
            Log.d(TAG, "Clicked on ${it.title}")
            val bundle = Bundle()
            bundle.putInt("movie_id", it.id)
            MovieDetailFragment().arguments = bundle
            findNavController().navigate(R.id.action_mainFragment_to_movieDetailFragment, bundle)
        }
        )
    }


}