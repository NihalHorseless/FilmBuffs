package com.example.filmbuffs.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class LocalMovie(
    @PrimaryKey val movieId: Int,
    val movieName: String?
)
