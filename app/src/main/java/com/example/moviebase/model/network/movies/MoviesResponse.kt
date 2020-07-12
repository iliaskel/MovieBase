package com.example.moviebase.model.network.movies

import com.example.moviebase.model.network.Response

interface MoviesResponse : Response {
    val page: Int?
    val results: List<MoviesResult?>?
    val totalPages: Int?
    val totalResults: Int?
}