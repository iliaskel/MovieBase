package com.example.moviebase.model.network.simplemovie

interface SimpleMovieResponse {
    val page: Int?
    val results: List<SimpleMovieResult?>?
    val totalPages: Int?
    val totalResults: Int?
}