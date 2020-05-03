package com.example.moviebase.model.network.movies

import com.google.gson.annotations.SerializedName

data class PopularResponse(
    override val page: Int,
    override val results: List<MoviesResult?>?,
    @SerializedName("total_pages")
    override val totalPages: Int,
    @SerializedName("total_results")
    override val totalResults: Int
) : MoviesResponse