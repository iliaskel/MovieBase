package com.example.moviebase.model.representation

import com.example.moviebase.model.database.entity.MovieType

data class Movie(
    private val id: Int,
    private val title: String,
    private val posterPath: String,
    private val releaseDate: String,
    private val voteAverage: Double,
    private val voteCount: Int,
    private val type: MovieType,
    private val clickAction: (()-> Unit)?
)