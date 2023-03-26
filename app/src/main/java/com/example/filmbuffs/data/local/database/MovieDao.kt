package com.example.filmbuffs.data.local.database

import androidx.room.*

@Dao
interface MovieDao {
    @Upsert
    suspend fun insert(vararg movies: LocalMovie)

    @Delete
    suspend fun delete(movie: LocalMovie)

    @Query("SELECT * FROM movies")
    suspend fun getAll(): List<LocalMovie>

    @Query("SELECT * FROM movies WHERE movieName = :searchedMovie")
    suspend fun loadSearchedMovie(searchedMovie: String): List<LocalMovie>

}