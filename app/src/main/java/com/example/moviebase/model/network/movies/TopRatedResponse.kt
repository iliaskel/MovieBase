package com.example.moviebase.model.network.movies

import com.example.moviebase.model.database.entity.MovieType
import com.google.gson.annotations.SerializedName

data class TopRatedResponse(
    override val page: Int?,
    override val results: List<MoviesResult?>?,
    @SerializedName("total_pages")
    override val totalPages: Int?,
    @SerializedName("total_results")
    override val totalResults: Int?,
    val type: MovieType = MovieType.TOP_RATED
) : IMoviesResponse