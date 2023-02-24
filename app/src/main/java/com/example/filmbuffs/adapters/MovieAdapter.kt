package com.example.filmbuffs.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.navigation.findNavController

import androidx.recyclerview.widget.RecyclerView
import com.example.filmbuffs.fragments.MovieDetailFragment
import com.example.filmbuffs.R
import com.example.filmbuffs.databinding.MoviePosterWithTitleBinding
import com.example.filmbuffs.fragments.MainFragment
import com.example.filmbuffs.fragments.MainFragmentDirections
import com.example.filmbuffs.models.popularmoviemodel.TotalResults
import com.example.filmbuffs.models.popularmoviemodel.Movie
import com.squareup.picasso.Picasso

internal class MovieAdapter( val context: Context)
    : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = MoviePosterWithTitleBinding.bind(itemView)
        val txtTitle = binding.movieTitle
        val imgPoster = binding.moviePoster
    }
    private var movieList: List<Movie> = emptyList()
    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_poster_with_title, parent, false)
        return ViewHolder(view)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }
    override fun getItemCount(): Int {
        return movieList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie: Movie = movieList[position]
        holder.txtTitle.setText(movie.title)
        Picasso.with(context)
            .load("https://image.tmdb.org/t/p/original" + movie.posterPath)
            .placeholder(R.drawable.ic_action_placeholder)
            .error(R.drawable.ic_action_error_placeholder)
            .into(holder.imgPoster)
        holder.imgPoster.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("movie_id",holder.itemId.toInt())
            MovieDetailFragment().arguments = bundle
            val action = MainFragmentDirections.actionMainFragmentToMovieDetailFragment2()
            it.findNavController().navigate(action)
        }

    }

    fun updateMovies(movies: List<Movie>) {
        movieList = movies
        notifyDataSetChanged()

    }
}