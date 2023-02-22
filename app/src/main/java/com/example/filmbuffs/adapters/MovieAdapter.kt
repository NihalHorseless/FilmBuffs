package com.example.filmbuffs.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.filmbuffs.fragments.MovieDetailFragment
import com.example.filmbuffs.R
import com.example.filmbuffs.databinding.MoviePosterWithTitleBinding
import com.example.filmbuffs.models.popularmoviemodel.TotalResults
import com.example.filmbuffs.models.popularmoviemodel.Movie
import com.squareup.picasso.Picasso

internal class MovieAdapter(val movies: List<Movie>, val activity: Activity)
    : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = MoviePosterWithTitleBinding.bind(itemView)
        val txtTitle = binding.movieTitle
        val imgPoster = binding.moviePoster
    }
    private var movieList: List<Movie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_poster_with_title, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie: Movie = movies[position]
        holder.txtTitle.setText(movie.title)
        Picasso.with(activity)
            .load("https://image.tmdb.org/t/p/original" + movie.posterPath)
            .placeholder(R.drawable.ic_action_placeholder)
            .error(R.drawable.ic_action_error_placeholder)
            .into(holder.imgPoster)
        holder.imgPoster.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(activity, MovieDetailFragment::class.java)
                intent.putExtra("movie_id", movie.id)
               // v?.findNavController()?.navigate(R.id.action_mainFragment_to_movieDetailFragment2)

            }
        })

    }

    fun updateMovies(movies: List<Movie>) {
        movieList = movies
        notifyDataSetChanged()

    }
}