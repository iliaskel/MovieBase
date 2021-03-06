package com.example.moviebase.model.network.tvshows

import com.google.gson.annotations.SerializedName

data class UpcomingTvShowsResponse(
    override val page: Int?,
    override val results: List<TvShowResult?>?,
    @SerializedName("total_pages")
    override val totalPages: Int?,
    @SerializedName("total_results")
    override val totalResults: Int?
) : TvShowsResponse