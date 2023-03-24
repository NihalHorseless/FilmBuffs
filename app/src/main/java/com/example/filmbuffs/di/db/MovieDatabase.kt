package com.example.filmbuffs.di.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.filmbuffs.data.local.database.LocalMovie
import com.example.filmbuffs.data.local.database.MovieDao

@Database(entities = [LocalMovie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var dbInstance: MovieDatabase? = null

        fun getInstance(
            context: Context
        ): MovieDatabase {
            synchronized(this) {
                return dbInstance ?: Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "MovieDatabase_Impl"
                ).build().also {
                    dbInstance = it
                }
            }
        }
    }

}
