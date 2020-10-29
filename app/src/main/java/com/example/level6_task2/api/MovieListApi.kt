package com.example.level6_task2.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieListApi {
    companion object {
        // The base url off the api.
        private const val baseUrl = "https://api.themoviedb.org"

        /**
         * @return [MovieListApiService] The service class off the retrofit client.
         */
        fun createApi(): MovieListApiService{
            // Create an OkHttpClient to be able to make a log of the network traffic
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            // Create the Retrofit instance
            val movieListApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Return the Retrofit movieListApiService
            return movieListApi.create(MovieListApiService::class.java)
        }
    }
}