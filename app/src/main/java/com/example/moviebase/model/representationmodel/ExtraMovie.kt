package com.example.moviebase.model.representationmodel

import com.example.moviebase.model.database.entity.ExtraMovieType

data class ExtraMovie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val extraMovieType: ExtraMovieType,
    val clickAction: (() -> Unit)?
)