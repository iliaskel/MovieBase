package com.example.moviebase.model.network.detailedmovie

interface DetailedMovieResult {
    val id: Int?
    val popularity: Double?
    val originalTitle: String?
    val posterPath: String?
    val releaseDate: String?
    val voteAverage: Double?
    val voteCount: Int?
}