package com.example.level6_task2.api

import com.example.level6_task2.BuildConfig
import com.example.level6_task2.model.MovieList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieListApiService {

    // The GET method needed to retrieve a random number trivia.
    @GET("/3/discover/movie?api_key="+BuildConfig.TMDB_API_KEY+"&language=en-US&include_adult=false&include_video=false&page=1")
    suspend fun getMovieListByYear(@Query("primary_release_year")year: String): MovieList
}