package com.example.moviebase.model.representation.movies

interface IMovie {
    val id: Int
    val title: String
    val posterPath: String
    val releaseDate: String
    val voteAverage: Double
    val voteCount: Int
    val clickAction: (() -> Unit)?
}