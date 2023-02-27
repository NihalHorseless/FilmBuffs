package com.example.filmbuffs.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmbuffs.R
import com.example.filmbuffs.adapters.MovieAdapter
import com.example.filmbuffs.models.popularmoviemodel.Movie
import com.example.filmbuffs.viewmodels.MainViewModel

class MainFragment : Fragment() {

    private val TAG = "Main Fragment"
    private lateinit var navController: NavController
    //
    private val myViewModel: MainViewModel by viewModels()
    //
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main,container,false)
        Log.d(TAG,"Entered view")
        recyclerview = view.findViewById(R.id.movielist)
        recyclerview?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        adapter = MovieAdapter()
        recyclerview?.adapter = adapter
        myViewModel.getMovies()
        myViewModel.movies.observe(viewLifecycleOwner,{ movies -> adapter?.updateMovies(movies) })
        adapter.setOnItemClickListener(MovieAdapter.OnItemClickListener {
            Log.d(TAG, "Clicked on ${it.title}")
            val bundle = Bundle()
            bundle.putInt("movie_id", it.id)
            MovieDetailFragment().arguments = bundle
            findNavController().navigate(R.id.action_mainFragment_to_movieDetailFragment, bundle)
        }
        )
        return view
    }


}