package com.example.filmbuffs.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmbuffs.adapters.FavoriteMovieAdapter
import com.example.filmbuffs.databinding.FragmentFavoriteMoviesBinding
import com.example.filmbuffs.repository.MovieRepository
import com.example.filmbuffs.viewmodels.FavoriteMoviesViewModel

class FavoriteMoviesFragment : Fragment() {
    private val TAG = "FavoriteFragment"

    // You have to create a factory!
    // View Model
    private lateinit var favoriteViewModel: FavoriteMoviesViewModel

    // Fields
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: FavoriteMovieAdapter

    //Binding
    private lateinit var binding: FragmentFavoriteMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteViewModel =
            ViewModelProvider(
                this,
                FavoriteMoviesViewModel.FavoriteMoviesViewModelFactory(
                    MovieRepository(
                        requireContext()
                    )
                )
            ).get(
                FavoriteMoviesViewModel::class.java
            )
        initializeFields()
    }

    private fun initializeFields() {
        recyclerView = binding.favList
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        movieAdapter = FavoriteMovieAdapter()
        recyclerView.adapter = movieAdapter

        favoriteViewModel.getAllMovies()
        favoriteViewModel.favMovies.observe(viewLifecycleOwner) { favMovies ->
            movieAdapter.updateLocalMovies(favMovies)
        }

        movieAdapter.setOnItemClickListener(FavoriteMovieAdapter.OnItemClickListener {
            val action =
                FavoriteMoviesFragmentDirections.actionFavoriteMoviesToMovieDetailFragment()
                    .setMovieId(it.movieId)

            binding.root.findNavController().navigate(action)

        })
    }


}