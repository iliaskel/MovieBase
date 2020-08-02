package com.example.moviebase.model.representation

import com.example.moviebase.model.representation.movies.MovieEntryModel

data class MoviesRecyclerViewItemModel(
    override val title: String,
    val moviesEntriesList: List<MovieEntryModel>
) : IMovieRecyclerItemModel