package com.example.moviebase.model.database.entity

interface SimpleMovieEntity {
    val id: Int
    val title: String
    val posterPath: String
    val releaseDate: String
    val voteAverage: Double
    val voteCount: Int
}