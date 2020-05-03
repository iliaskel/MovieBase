package com.example.moviebase.model.network.detailedmovie

import com.google.gson.annotations.SerializedName

data class SimilarMovies(
    val page: Int?,
    val results: List<SimilarMoviesResult?>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)