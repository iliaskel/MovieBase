package com.example.moviebase.model.network.searchmovie

import com.google.gson.annotations.SerializedName

data class SearchMovieResponse(
    val page: Int?,
    val results: List<SearchMovieResult?>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)