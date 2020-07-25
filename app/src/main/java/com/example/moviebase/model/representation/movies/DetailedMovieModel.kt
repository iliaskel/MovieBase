package com.example.moviebase.model.representation.movies

import android.view.View

data class DetailedMovieModel(
    override val id: Int,
    override val title: String,
    override val posterPath: String,
    val description: String,
    override val releaseDate: String,
    override val voteAverage: Double,
    override val voteCount: Int,
    override val clickAction: (view: View) -> Unit?
) : IMovie