package com.example.moviebase.model.representation

data class ExtraMoviesRecyclerViewItemModel(
    override val title: String,
    val moviesEntriesList: List<ExtraMoviesRecyclerViewItemModel>
) : IMovieRecyclerItemModel