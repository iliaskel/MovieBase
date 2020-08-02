package com.example.moviebase.model.network.detailedmovie.tvshows

import com.example.moviebase.model.network.detailedmovie.DetailedMovieResult
import com.example.moviebase.model.network.detailedmovie.DetailedMovieSimilarMovies
import com.google.gson.annotations.SerializedName

data class DetailedTvShowSimilar(
    override val page: Int,
    override val detailedMoviesResult: List<DetailedMovieResult?>?,
    @SerializedName("total_pages")
    override val totalPages: Int,
    @SerializedName("total_results")
    override val totalResults: Int
) : DetailedMovieSimilarMovies