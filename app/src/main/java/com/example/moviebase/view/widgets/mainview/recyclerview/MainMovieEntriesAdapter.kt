package com.example.moviebase.view.widgets.mainview.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.moviebase.R
import com.example.moviebase.model.representation.MovieEntryModel

class MainMovieEntriesAdapter :
    ListAdapter<MovieEntryModel, MainMovieItemViewHolder>(
        MainMoviesDiffCallback()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMovieItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_movie_tile, parent, false)
        return MainMovieItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainMovieItemViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bindMovieViewHolder(movie)
    }

}