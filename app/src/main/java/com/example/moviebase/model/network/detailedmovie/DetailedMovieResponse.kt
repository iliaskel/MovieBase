package com.example.moviebase.model.network.detailedmovie

import com.example.moviebase.model.network.detailedmovie.movies.DetailedMovieRecommendations

interface DetailedMovieResponse {
    val id: Int?
    val overview: String?
    val popularity: Double?
    val recommendations: DetailedMovieRecommendations?
    val similar: DetailedMovieSimilarMovies?
    val status: String?
    val posterPath: String?
    val releaseDate: String?
    val originalTitle: String?
    val voteAverage: Double?
    val voteCount: Int?
}