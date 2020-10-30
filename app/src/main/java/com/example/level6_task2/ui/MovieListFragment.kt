package com.example.level6_task2.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.level6_task2.R
import com.example.level6_task2.adapter.MovieListAdapter
import com.example.level6_task2.model.Movie
import com.example.level6_task2.viewmodels.MovieListViewModel
import com.example.level6_task2.viewmodels.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movie_list.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

class MovieListFragment : Fragment() {

    private var movieList = arrayListOf<Movie>()
    private lateinit var movieListAdapter: MovieListAdapter

    private val movieListViewModel: MovieListViewModel by viewModels()
    private lateinit var movieViewModel: MovieViewModel


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
            val year = etYear.text.toString()
            movieListViewModel.getMovieListByYear(year)
        }

        movieViewModel= ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)

        movieListAdapter = MovieListAdapter(movieList, ::onColorClick)
        rvMovies.layoutManager =
            GridLayoutManager(context, 2)
        rvMovies.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rvMovies.adapter = movieListAdapter

        rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val year = etYear.text.toString()
                    movieListViewModel.addMoreMovies()
                }
            }
        })

        observeMovieList()
    }

    private fun observeMovieList() {
        movieListViewModel.movieList.observe(viewLifecycleOwner, {
            Log.i("OBSERVE", "LIST->LIST")
            movieList.clear()
            movieList.addAll(it.results)
            movieListAdapter.notifyDataSetChanged()
        })

        movieViewModel.movie.observe(viewLifecycleOwner, {
            Log.i("OBSERVE", "LIST->INFO")
            Log.i("OK", "JA HIER DOET DIE HET WEL")
            Log.i("OK", it?.title)
        })
    }

    private fun onColorClick(movie: Movie) {

        movieViewModel.setMovie(movie)

        findNavController().navigate(R.id.action_MovieListFragment_to_MovieInfoFragment)
    }
}