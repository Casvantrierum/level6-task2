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

    private val _fetching: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * Expose non MutableLiveData via getter
     * Encapsulation :)
     */
    val movieList: LiveData<MovieList>
        get() = _movieList

    val fetching: LiveData<Boolean>
        get() = _fetching

    private lateinit var lastSearchedYear: String

    /**
     * suspend function that calls a suspend function from the numbersApi call
     */
    suspend fun getMovieListByYear(year: String)  {
        _fetching.value = true
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
        _fetching.value = false
    }

    suspend fun addMoreMovies()  {
        _fetching.value = true
        try {
            val page = _movieList.value?.page!! +1
            //timeout the request after 5 seconds
            val result = withTimeout(5_000) {
                movieListApiService.addMoreMovies(lastSearchedYear, page)
            }

            //add current list to new items
            result.results.addAll(0, _movieList.value!!.results)
            _movieList.value = result
        } catch (error: Throwable) {
            throw MovieListRefreshError("Unable to refresh movie list", error)
        }
        _fetching.value = false
    }

    class MovieListRefreshError(message: String, cause: Throwable) : Throwable(message, cause)

}
