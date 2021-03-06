package com.example.level6_task2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.level6_task2.R
import com.example.level6_task2.model.Movie
import com.example.level6_task2.viewmodels.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movie_info.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MovieInfoFragment : Fragment() {


    private lateinit var movie: Movie

    // for shared viewmodels between fragments
    // https://developer.android.com/topic/libraries/architecture/viewmodel#sharing
    private val movieViewModel: MovieViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //movieViewModel= ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)

        observeMovie()
    }
    
    private fun observeMovie() {
        movieViewModel.movie.observe(viewLifecycleOwner, {
            it?.let{
                movie = it
                updateUI()
                tvMovieTitle.text = it.title
        }})
    }

    private fun updateUI() {
        activity?.let { Glide.with(it).load(movie.getBackdropImageUrl()).into(ivBackdrop) }
        activity?.let { Glide.with(it).load(movie.getPosterImageUrl()).into(ivPoster) }

        tvMovieTitle.text = movie.title
        tvRelease.text = movie.release_date
        tvRating.text = movie.vote_average.toString()
        tvOverview.text = movie.overview

        //set rating star
        ratingBar.rating = ((movie.vote_average/10).toFloat())
    }
}