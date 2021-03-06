package com.example.moviebase.model.representation.movies

import android.view.View
import com.example.moviebase.model.database.entity.MovieType

data class MovieEntryModel(
    override val id: Int,
    override val title: String,
    override val posterPath: String,
    override val releaseDate: String,
    override val voteAverage: Double,
    override val voteCount: Int,
    override val clickAction: (view: View) -> Unit?,
    val movieType: MovieType
) : IMovie