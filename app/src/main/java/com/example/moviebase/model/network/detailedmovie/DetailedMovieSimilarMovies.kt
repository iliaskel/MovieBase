package com.example.moviebase.model.network.detailedmovie

interface DetailedMovieSimilarMovies {
    val page: Int?
    val detailedMoviesResult: List<DetailedMovieResult?>?
    val totalPages: Int?
    val totalResults: Int?
}