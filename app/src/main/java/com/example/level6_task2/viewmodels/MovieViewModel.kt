package com.example.level6_task2.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.level6_task2.model.Movie
import com.example.level6_task2.repository.MovieListRepository
import com.example.level6_task2.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel (application: Application) : AndroidViewModel(application) {

    private val movieRepository = MovieRepository()

    /**
     * This property points direct to the LiveData in the repository, that value
     * get's updated when user clicks FAB. This happens through the getMovieListNumber() in this class :)
     */
    val movie = movieRepository.movie

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
    fun setMovie(movieToView: Movie) {
        viewModelScope.launch {
            try {
                //the MovieListRepository sets it's own livedata property
                //our own MovieList LiveData property points to te one in that repository
                movieRepository.setMovie(movieToView)
            } catch (error: MovieListRepository.MovieListRefreshError) {
                _errorText.value = error.message
            }
        }
    }

}