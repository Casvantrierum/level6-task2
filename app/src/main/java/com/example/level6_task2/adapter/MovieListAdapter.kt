package com.example.level6_task2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.level6_task2.R
import com.example.level6_task2.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieListAdapter (private val movieList: List<Movie>) :
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
        holder.databind(movieList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun databind(movie: Movie) {
            itemView.tvTitle.text = movie.title
        }

//        init {
//            itemView.setOnClickListener { onClick(colors[adapterPosition]) }
//        }

//        fun bind(colorItem: ColorItem) {
//            Glide.with(context).load(colorItem.getImageUrl()).into(itemView.ivColor)
//        }
    }

}
