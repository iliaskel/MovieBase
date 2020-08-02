package com.example.moviebase.model.network.detailedmovie.movies

import com.example.moviebase.model.network.detailedmovie.DetailedMovieRecommendations
import com.google.gson.annotations.SerializedName

data class DetailedMovieRecommendations(
    override val page: Int?,
    @SerializedName("results")
    override val results: List<DetailedMovieResult?>?,
    @SerializedName("total_pages")
    override val totalPages: Int?,
    @SerializedName("total_results")
    override val totalResults: Int?
) : DetailedMovieRecommendations