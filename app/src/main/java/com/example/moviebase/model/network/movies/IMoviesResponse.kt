package com.example.moviebase.model.network.movies

import com.example.moviebase.model.network.Response

interface IMoviesResponse : Response {
    val page: Int?
    val results: List<MoviesResult?>?
    val totalPages: Int?
    val totalResults: Int?
}