package com.example.level6_task2.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.level6_task2.BuildConfig
import com.example.level6_task2.R
import com.example.level6_task2.ViewModel.MovieListViewModel
import com.example.level6_task2.adapter.MovieListAdapter
import com.example.level6_task2.model.Movie
import kotlinx.android.synthetic.main.fragment_movie_list.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MovieListFragment : Fragment() {

    private val movieList = arrayListOf<Movie>()
    private lateinit var movieListAdapter: MovieListAdapter
    private val viewModel: MovieListViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnSubmit).setOnClickListener {
            viewModel.getMovieListByYear()
            //findNavController().navigate(R.id.action_MovieListFragment_to_MovieInfoFragment)
        }

        movieListAdapter = MovieListAdapter(movieList)
        rvMovies.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvMovies.adapter = movieListAdapter

        observeMovieList()
    }

    private fun observeMovieList() {
        viewModel.movieList.observe(viewLifecycleOwner, {
            movieList.clear()
            movieList.addAll(it.results)
            movieListAdapter.notifyDataSetChanged()
        })
    }
}