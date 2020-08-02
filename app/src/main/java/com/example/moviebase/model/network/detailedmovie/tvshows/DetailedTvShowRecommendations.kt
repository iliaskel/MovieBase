package com.example.moviebase.model.network.detailedmovie.tvshows

import com.example.moviebase.model.network.detailedmovie.DetailedMovieRecommendations
import com.google.gson.annotations.SerializedName

data class DetailedTvShowRecommendations(
    override val page: Int,
    override val results: List<DetailedTvShowResult?>?,
    @SerializedName("total_pages")
    override val totalPages: Int,
    @SerializedName("total_results")
    override val totalResults: Int
) : DetailedMovieRecommendations