package com.example.moviebase.model.database.entity

interface DetailedMovieEntity {
    val id: Int
    val title: String
    val overview: String
    val posterPath: String
    val releaseDate: String
    val voteAverage: Double
    val voteCount: Int
}