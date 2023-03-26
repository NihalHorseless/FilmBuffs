package com.example.filmbuffs.ui.details

import android.util.Log
import androidx.lifecycle.*
import com.example.filmbuffs.data.local.database.LocalMovie
import com.example.filmbuffs.data.remote.models.castmodel.Cast
import com.example.filmbuffs.data.remote.models.singlemoviemodel.SingleMovieDetail
import com.example.filmbuffs.data.repository.MovieRepository
import com.example.filmbuffs.di.networkcalls.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailViewModel(private val repository: MovieRepository) : ViewModel() {

    private val TAG: String = "MovieDetailViewModel"

    private val _movie = MutableLiveData<SingleMovieDetail>()
    val movie: LiveData<SingleMovieDetail>
        get() = _movie

    private val _cast = MutableLiveData<List<Cast>>()
    val cast: LiveData<List<Cast>>
        get() = _cast

    //Retrofit instance
    private val apiService = NetworkModule.moviesApi

    fun addMovie(favMovie: LocalMovie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMovie(favMovie)
        }
    }
    fun delete(movie: LocalMovie) {
        viewModelScope.launch {
            repository.delete(movie)
        }
    }

    fun getMovieById(movieId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getMovie(movieId)
                if (response.isSuccessful) {
                    val movie = response.body()!!
                    _movie.postValue(movie)
                }

            } catch (e: Exception) {
                Log.d(TAG, "Can't connect!")
            }
        }
    }

    fun getCastById(movieId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getCast(movieId)
                if (response.isSuccessful) {
                    val cast = response.body()!!.cast
                    _cast.postValue(cast)
                }

            } catch (e: Exception) {
                Log.d(TAG, "Can't connect!")
            }
        }
    }

    class MovieDetailViewModelFactory(private val repository: MovieRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")

        }
    }
}