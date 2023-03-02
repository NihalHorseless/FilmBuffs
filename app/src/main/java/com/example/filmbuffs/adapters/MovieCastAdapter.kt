package com.example.filmbuffs.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.filmbuffs.R
import com.example.filmbuffs.databinding.MovieCastItemBinding
import com.example.filmbuffs.models.castmodel.Cast
import com.example.filmbuffs.util.Constants
import com.squareup.picasso.Picasso

internal class MovieCastAdapter
    : RecyclerView.Adapter<MovieCastAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = MovieCastItemBinding.bind(itemView)
        val txtName = binding.actorname
        val actorImg = binding.actorImg
        val progressbar: ProgressBar = binding.actorImgProgressBar
        fun bindItems(cast: Cast) {
            txtName.text = cast.name
            progressbar.visibility = View.VISIBLE
            Picasso.with(itemView.context)
                .load(Constants.BASE_URL_PERSON + cast.profilePath)
                .error(R.drawable.ic_action_error_placeholder)
                .noFade()
                .into(actorImg, object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        progressbar.visibility = View.GONE
                    }

                    override fun onError() {
                        progressbar.visibility = View.GONE
                    }
                })
        }
    }

    private var castList: List<Cast> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_cast_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return castList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cast: Cast = castList[position]
        holder.bindItems(cast)
    }

    fun updateMovies(actors: List<Cast>) {
        castList = actors
        notifyDataSetChanged()

    }
}