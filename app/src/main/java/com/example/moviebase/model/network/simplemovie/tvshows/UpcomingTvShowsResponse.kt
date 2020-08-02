package com.example.moviebase.model.network.simplemovie.tvshows

import com.example.moviebase.model.network.simplemovie.SimpleMovieResponse
import com.example.moviebase.model.network.simplemovie.SimpleMovieResult
import com.google.gson.annotations.SerializedName

data class UpcomingTvShowsResponse(
    override val page: Int?,
    override val results: List<SimpleMovieResult?>?,
    @SerializedName("total_pages")
    override val totalPages: Int?,
    @SerializedName("total_results")
    override val totalResults: Int?
) : SimpleMovieResponse