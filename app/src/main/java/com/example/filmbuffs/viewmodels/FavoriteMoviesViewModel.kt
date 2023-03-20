package com.example.filmbuffs.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.filmbuffs.database.LocalMovie
import com.example.filmbuffs.models.singlemoviemodel.SingleMovieDetail
import com.example.filmbuffs.networkcalls.NetworkModule
import com.example.filmbuffs.repository.MovieRepository
import kotlinx.coroutines.launch

class FavoriteMoviesViewModel(private val repository: MovieRepository) : ViewModel() {
 private val TAG = "FavoriteMoviesViewModel"

    private val _favMovies = MutableLiveData<List<LocalMovie>>()
    val favMovies: LiveData<List<LocalMovie>>
        get() = _favMovies

    fun delete(movie: LocalMovie) {
        viewModelScope.launch {
            repository.delete(movie)
        }
    }
    fun getAllMovies() {
        viewModelScope.launch { try {
            val response = repository.getAll()
            _favMovies.value = response
        }
            catch (e: Exception){
                Log.d(TAG,e.message!!)
            }
        }
    }
    class FavoriteMoviesViewModelFactory(private val repository: MovieRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavoriteMoviesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FavoriteMoviesViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")

        }
    }
}

