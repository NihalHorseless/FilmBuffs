package com.example.filmbuffs.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert
    suspend fun insert(vararg movies: LocalMovie)

    @Delete
    suspend fun delete(movie: LocalMovie)

    @Query("SELECT * FROM movies")
    suspend fun getAll(): List<LocalMovie>

    @Query("SELECT * FROM movies WHERE movieName = :searchedMovie")
    suspend fun loadSearchedMovie(searchedMovie: String): List<LocalMovie>
}