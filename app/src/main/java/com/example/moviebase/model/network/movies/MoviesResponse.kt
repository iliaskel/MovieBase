package com.example.moviebase.model.network.movies

interface MoviesResponse {
    val page: Int?
    val results: List<MoviesResult?>?
    val totalPages: Int?
    val totalResults: Int?
}