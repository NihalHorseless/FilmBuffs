package com.example.filmbuffs.data.repository

import android.content.Context
import com.example.filmbuffs.data.local.database.LocalMovie
import com.example.filmbuffs.di.db.MovieDatabase

class MovieRepository(private val context: Context) {

    suspend fun addMovie(movie: LocalMovie) =
        MovieDatabase.getInstance(context).movieDao().insert(movie)

    suspend fun delete(movie: LocalMovie) = MovieDatabase.getInstance(context).movieDao().delete(movie)

    suspend fun getAll() = MovieDatabase.getInstance(context).movieDao().getAll()

    suspend fun loadSearchedMovie(searchedMovie: String): List<LocalMovie> =
        MovieDatabase.getInstance(context).movieDao().loadSearchedMovie(searchedMovie)

}