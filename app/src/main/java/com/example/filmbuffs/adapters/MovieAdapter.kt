package com.example.filmbuffs.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController

import androidx.recyclerview.widget.RecyclerView
import com.example.filmbuffs.MainFragment
import com.example.filmbuffs.MovieDetailFragment
import com.example.filmbuffs.R
import com.example.filmbuffs.databinding.MoviePosterWithTitleBinding
import com.example.filmbuffs.objects.Movies
import com.squareup.picasso.Picasso

internal class MovieAdapter(val movies: List<com.example.filmbuffs.objects.Result>,
                            val activity:Activity): RecyclerView.Adapter<MovieAdapter.ViewHolder>(){
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = MoviePosterWithTitleBinding.bind(itemView)
        val txtTitle = binding.movieTitle
        val imgPoster = binding.moviePoster
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_poster_with_title,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie:com.example.filmbuffs.objects.Result = movies[position]
        holder.txtTitle.setText(movie.title)
        Picasso.with(activity)
            .load("https://image.tmdb.org/t/p/original"+movie.posterPath)
            .placeholder(R.drawable.ic_action_placeholder)
            .error(R.drawable.ic_action_error_placeholder)
            .into(holder.imgPoster)
        holder.imgPoster.setOnClickListener {
            val intent = Intent(activity,MovieDetailFragment::class.java)
            intent.putExtra("description_key",movie.overview)
            intent.putExtra("image_url",movie.posterPath)

        }

    }
    fun onClickPopularMovie(){

    }
}