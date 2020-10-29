package com.example.level6_task2.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.level6_task2.repository.MovieListRepository
import kotlinx.coroutines.launch

class MovieListViewModel(application: Application) : AndroidViewModel(application) {

    private val movieListRepository = MovieListRepository()

    /**
     * This property points direct to the LiveData in the repository, that value
     * get's updated when user clicks FAB. This happens through the getMovieListNumber() in this class :)
     */
    val movieList = movieListRepository.movieList

    private val _errorText: MutableLiveData<String> = MutableLiveData()

    /**
     * Expose non MutableLiveData via getter
     * errorText can be observed from view for error showing
     * Encapsulation :)
     */
    val errorText: LiveData<String>
        get() = _errorText

    /**
     * The viewModelScope is bound to Dispatchers.Main and will automatically be cancelled when the ViewModel is cleared.
     * Extension method of lifecycle-viewmodel-ktx library
     */
    fun getMovieListByYear() {
        viewModelScope.launch {
            try {
                //the MovieListRepository sets it's own livedata property
                //our own MovieList LiveData property points to te one in that repository
                movieListRepository.getMovieListByYear()
            } catch (error: MovieListRepository.MovieListRefreshError) {
                _errorText.value = error.message
                Log.e("MovieList error", error.cause.toString())
            }
        }
    }
}