package com.example.moviebase.model.network.detailedmovie

interface DetailedMovieRecommendations {
    val page: Int?
    val results: List<DetailedMovieResult?>?
    val totalPages: Int?
    val totalResults: Int?
}