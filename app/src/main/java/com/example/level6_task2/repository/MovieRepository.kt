package com.example.level6_task2.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.level6_task2.model.Movie

class MovieRepository{

    private val _movie: MutableLiveData<Movie> = MutableLiveData()

    /**
     * Expose non MutableLiveData via getter
     * Encapsulation :)
     */
    val movie: LiveData<Movie?>
        get() = _movie

    /**
     * suspend function that calls a suspend function from the numbersApi call
     */
    fun setMovie(movieToView: Movie)  {
        try {

            _movie.value = movieToView
        } catch (error: Throwable) {
            throw MovieListRefreshError("Unable to refresh movie list", error)
        }
    }

    class MovieListRefreshError(message: String, cause: Throwable) : Throwable(message, cause)

}