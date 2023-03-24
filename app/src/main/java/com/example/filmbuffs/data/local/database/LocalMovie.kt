package com.example.filmbuffs.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class LocalMovie(
    @PrimaryKey val movieId: Int,
    val movieName: String?,
    val movieBannerUrl: String?
)
