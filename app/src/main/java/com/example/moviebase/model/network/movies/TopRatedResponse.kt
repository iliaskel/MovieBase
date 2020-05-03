package com.example.moviebase.model.network.movies

import com.google.gson.annotations.SerializedName

data class TopRatedResponse(
    val page: Int?,
    val results: List<MoviesResult?>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)