package com.example.moviebase.model.network.upcoming

import com.google.gson.annotations.SerializedName

data class UpcomingResponse(
    val page: Int?,
    val results: List<UpcomingMoviesResult?>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)