package com.example.filmbuffs.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert
    fun insertAll(vararg movies: LocalMovie)

    @Delete
    fun delete(movie: LocalMovie)

    @Query("SELECT * FROM movies")
    fun getAll(): Flow<LocalMovie>

    @Query("SELECT * FROM movies WHERE movieName = :searchedMovie")
    fun loadSearchedMovie(searchedMovie: String): Array<LocalMovie>
}