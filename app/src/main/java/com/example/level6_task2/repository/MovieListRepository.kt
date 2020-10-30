package com.example.level6_task2.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.level6_task2.api.MovieListApi
import com.example.level6_task2.api.MovieListApiService
import com.example.level6_task2.model.Movie
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

    private lateinit var lastSearchedYear: String

    /**
     * suspend function that calls a suspend function from the numbersApi call
     */
    suspend fun getMovieListByYear(year: String)  {
        try {
            //timeout the request after 5 seconds
            val result = withTimeout(5_000) {
                movieListApiService.getMovieListByYear(year)
            }

            lastSearchedYear = year

            _movieList.value = result
        } catch (error: Throwable) {
            throw MovieListRefreshError("Unable to refresh movie list", error)
        }
    }

    suspend fun addMoreMovies()  {
        try {

            val page = _movieList.value?.page!! +1;
            //timeout the request after 5 seconds
            val result = withTimeout(5_000) {
                movieListApiService.addMoreMovies(lastSearchedYear, page)
            }

            var list = _movieList.value!!.results

            result.results.addAll(0, list)
            _movieList.value = result
        } catch (error: Throwable) {
            throw MovieListRefreshError("Unable to refresh movie list", error)
        }
    }

    class MovieListRefreshError(message: String, cause: Throwable) : Throwable(message, cause)

}
