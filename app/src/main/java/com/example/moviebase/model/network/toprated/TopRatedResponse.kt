package com.example.moviebase.model.network.toprated

import com.google.gson.annotations.SerializedName

data class TopRatedResponse(
    val page: Int?,
    val results: List<TopRatedMoviesResult?>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)