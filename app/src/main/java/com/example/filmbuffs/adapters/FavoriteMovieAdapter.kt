package com.example.filmbuffs.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.filmbuffs.R
import com.example.filmbuffs.database.LocalMovie
import com.example.filmbuffs.databinding.MoviePosterWithTitleBinding
import com.squareup.picasso.Picasso

internal class FavoriteMovieAdapter : RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = MoviePosterWithTitleBinding.bind(itemView)

        private val txtTitle = binding.movieTitle
        private val imgPoster = binding.moviePoster
        val progressbar: ProgressBar = binding.progressBar

        fun bindItems(movie: LocalMovie, listener: FavoriteMovieAdapter.OnItemClickListener) {
            txtTitle.text = movie.movieName
            progressbar.visibility = View.VISIBLE

            Picasso.with(itemView.context)
                .load("https://image.tmdb.org/t/p/original" + movie.movieBannerUrl)
                .error(R.drawable.ic_action_error_placeholder)
                .noFade()
                .into(imgPoster, object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        progressbar.visibility = View.GONE
                    }

                    override fun onError() {
                        progressbar.visibility = View.GONE
                    }

                })
            itemView.setOnClickListener {
                listener.onClick(movie)
            }

        }
    }

    private var movieList: List<LocalMovie> = emptyList()

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteMovieAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_poster_with_title, parent, false)
        return FavoriteMovieAdapter.ViewHolder(view)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return movieList.size
    }


    override fun onBindViewHolder(holder: FavoriteMovieAdapter.ViewHolder, position: Int) {
        val movie: LocalMovie = movieList[position]
        holder.bindItems(movie, listener!!)
    }

    fun updateLocalMovies(movies: List<LocalMovie>) {
        movieList = movies
        notifyDataSetChanged()

    }

    class OnItemClickListener(val clickListener: (movie: LocalMovie) -> Unit) {
        fun onClick(movie: LocalMovie) = clickListener(movie)
    }
}