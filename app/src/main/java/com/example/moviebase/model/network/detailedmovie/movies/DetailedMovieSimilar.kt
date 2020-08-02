package com.example.moviebase.model.network.detailedmovie.movies

import com.example.moviebase.model.network.detailedmovie.DetailedMovieSimilarMovies
import com.google.gson.annotations.SerializedName

data class DetailedMovieSimilar(
    override val page: Int?,
    @SerializedName("results")
    override val detailedMoviesResult: List<DetailedMovieResult?>?,
    @SerializedName("total_pages")
    override val totalPages: Int?,
    @SerializedName("total_results")
    override val totalResults: Int?
) : DetailedMovieSimilarMovies