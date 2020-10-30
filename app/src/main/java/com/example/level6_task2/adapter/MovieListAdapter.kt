package com.example.level6_task2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.level6_task2.R
import com.example.level6_task2.model.Movie
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieListAdapter (private val movieList: List<Movie>, private val onClick: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(movieList[position], position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun databind(movie: Movie, position: Int) {
            val ranking = position+1
            itemView.tvTitle.text = "$ranking."
            Glide.with(context).load(movie.getPosterImageUrl()).into(itemView.ivPoster)
        }

        init {
            itemView.setOnClickListener { onClick(movieList[adapterPosition]) }
        }
    }
}
