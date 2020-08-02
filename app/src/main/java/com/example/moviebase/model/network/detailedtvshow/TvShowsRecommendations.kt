package com.example.moviebase.model.network.detailedtvshow

import com.google.gson.annotations.SerializedName

data class TvShowsRecommendations(
    val page: Int,
    val results: List<TvShowsResult>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)