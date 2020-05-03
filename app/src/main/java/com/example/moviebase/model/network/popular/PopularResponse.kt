package com.example.moviebase.model.network.popular

import com.google.gson.annotations.SerializedName

data class PopularResponse(
    val page: Int,
    val results: List<PopularMoviesResult>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)