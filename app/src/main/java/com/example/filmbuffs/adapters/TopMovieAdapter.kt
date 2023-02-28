package com.example.filmbuffs.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.filmbuffs.R
import com.example.filmbuffs.databinding.MoviePosterWithTitleBinding
import com.squareup.picasso.Picasso

internal class TopMovieAdapter()
    : RecyclerView.Adapter<TopMovieAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = MoviePosterWithTitleBinding.bind(itemView)
        val txtTitle = binding.movieTitle
        val imgPoster = binding.moviePoster
        val progressbar: ProgressBar = binding.progressBar
        fun bindItems(topmovie: com.example.filmbuffs.models.topratedmovies.Result, listener: OnItemClickListener) {
            txtTitle.text = topmovie.title
            progressbar.visibility = View.VISIBLE
            Picasso.with(itemView.context)
                .load("https://image.tmdb.org/t/p/original" + topmovie.posterPath)
                .error(R.drawable.ic_action_error_placeholder)
                .noFade()
                .into(imgPoster,object: com.squareup.picasso.Callback{
                    override fun onSuccess() {
                        progressbar.visibility = View.GONE
                    }

                    override fun onError() {
                        progressbar.visibility = View.GONE
                    }

                })
            itemView.setOnClickListener {
                listener.onClick(topmovie)
            }

        }
    }
    private var topmovieList: List<com.example.filmbuffs.models.topratedmovies.Result> = emptyList()
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
        return topmovieList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val topmovie: com.example.filmbuffs.models.topratedmovies.Result = topmovieList[position]
        holder.bindItems(topmovie, listener!!)
    }
    fun updateMovies(topmovies: List<com.example.filmbuffs.models.topratedmovies.Result>) {
        topmovieList = topmovies
        notifyDataSetChanged()

    }
    class OnItemClickListener(val clickListener: (topmovie: com.example.filmbuffs.models.topratedmovies.Result) -> Unit) {
        fun onClick(topmovie: com.example.filmbuffs.models.topratedmovies.Result) = clickListener(topmovie)
    }
}