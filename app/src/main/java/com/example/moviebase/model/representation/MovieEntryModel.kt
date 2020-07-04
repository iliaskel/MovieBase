package com.example.moviebase.model.representation

import com.example.moviebase.model.database.entity.MovieType

data class MovieEntryModel(
    val id: Int,
    val title: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val type: MovieType,
    val clickAction: (() -> Unit)?
)