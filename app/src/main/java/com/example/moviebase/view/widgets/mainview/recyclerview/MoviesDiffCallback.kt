package com.example.moviebase.view.widgets.mainview.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.example.moviebase.model.representation.movies.MovieEntryModel

class MoviesDiffCallback : DiffUtil.ItemCallback<MovieEntryModel>() {

    override fun areItemsTheSame(oldItem: MovieEntryModel, newItem: MovieEntryModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieEntryModel, newItem: MovieEntryModel): Boolean {
        return oldItem.id == newItem.id && oldItem.posterPath == newItem.posterPath
                && oldItem.releaseDate == newItem.releaseDate && oldItem.voteAverage == newItem.voteAverage
                && oldItem.voteCount == newItem.voteCount && oldItem.title == newItem.title && oldItem.movieType == newItem.movieType
    }

}