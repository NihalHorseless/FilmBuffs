package com.example.filmbuffs.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.filmbuffs.R
import com.example.filmbuffs.databinding.MoviePosterWithTitleBinding

import com.example.filmbuffs.models.popularmoviemodel.Movie
import com.squareup.picasso.Picasso

internal class MovieAdapter() :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var movieList: List<Movie> = emptyList()
    private var listener: OnItemClickListener? = null


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = MoviePosterWithTitleBinding.bind(itemView)
        private val txtTitle = binding.movieTitle
        private val imgPoster = binding.moviePoster
        fun bindItems(movie: Movie, listener: OnItemClickListener) {
            txtTitle.text = movie.title
            Picasso.with(itemView.context)
                .load("https://image.tmdb.org/t/p/original" + movie.posterPath)
                .placeholder(R.drawable.ic_action_placeholder)
                .error(R.drawable.ic_action_error_placeholder)
                .into(imgPoster)

            itemView.setOnClickListener {
                listener.onClick(movie)
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_poster_with_title, parent, false)
        return ViewHolder(view)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return movieList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie: Movie = movieList[position]
        holder.bindItems(movie, listener!!)
    }

    fun updateMovies(movies: List<Movie>) {
        movieList = movies
        notifyDataSetChanged()
    }

    class OnItemClickListener(val clickListener: (movie: Movie) -> Unit) {
        fun onClick(movie: Movie) = clickListener(movie)
    }

}