package com.example.level6_task2.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.level6_task2.api.MovieListApi
import com.example.level6_task2.api.MovieListApiService
import com.example.level6_task2.model.MovieList
import kotlinx.coroutines.withTimeout

class MovieListRepository {
    private val movieListApiService: MovieListApiService = MovieListApi.createApi()

    private val _movieList: MutableLiveData<MovieList> = MutableLiveData()

    /**
     * Expose non MutableLiveData via getter
     * Encapsulation :)
     */
    val movieList: LiveData<MovieList>
        get() = _movieList

    /**
     * suspend function that calls a suspend function from the numbersApi call
     */
    suspend fun getMovieListByYear()  {
        try {
            //timeout the request after 5 seconds
            val result = withTimeout(5_000) {
                movieListApiService.getMovieListByYear()
            }

            _movieList.value = result
        } catch (error: Throwable) {
            throw MovieListRefreshError("Unable to refresh movie list", error)
        }
    }

    class MovieListRefreshError(message: String, cause: Throwable) : Throwable(message, cause)

}