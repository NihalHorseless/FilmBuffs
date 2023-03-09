package com.example.filmbuffs.fragments

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmbuffs.R
import com.example.filmbuffs.adapters.MovieAdapter
import com.example.filmbuffs.databinding.FragmentMainBinding
import com.example.filmbuffs.viewmodels.MainViewModel

class MainFragment : Fragment(), MenuProvider {

    private val TAG = "MainFragment"

    // View Model
    private val myViewModel: MainViewModel by viewModels()

    // Fields
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieTxt: TextView

    //Binding
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        initializeFields()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        myViewModel.movies.removeObservers(viewLifecycleOwner)
        activity?.removeMenuProvider(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.addMenuProvider(this, viewLifecycleOwner)
    }


    private fun initializeFields() {
        movieTxt = binding.popularmoviestxt

        recyclerView = binding.movielist
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        movieAdapter = MovieAdapter()
        recyclerView.adapter = movieAdapter
        showDefaultResults()
        movieAdapter.setOnItemClickListener(MovieAdapter.OnItemClickListener {
            val action =
                MainFragmentDirections.actionMainFragmentToMovieDetailFragment().setMovieId(it.id)

            binding.root.findNavController().navigate(action)

        })
    }

    fun search(query: String) {
        movieTxt.text = getString(R.string.searched_txt) + query

        myViewModel.searchMovies(query)
        myViewModel.movies.observe(viewLifecycleOwner) { movies ->
            if (isAdded) {
                movieAdapter.updateMovies(movies)
            }

        }
    }

    private fun showDefaultResults() {
        myViewModel.getMovies()
        myViewModel.movies.observe(viewLifecycleOwner) { movies ->
            movieAdapter.updateMovies(
                movies
            )
        }
        movieTxt.text = getString(R.string.popular_text)

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    search(query.trim())
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
    }


}