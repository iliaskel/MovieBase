package com.example.moviebase.model.network.tvshows

import com.example.moviebase.model.network.Response

interface TvShowsResponse : Response {
    val page: Int?
    val results: List<TvShowResult?>?
    val totalPages: Int?
    val totalResults: Int?
}