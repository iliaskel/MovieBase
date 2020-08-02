package com.example.moviebase.model.network.detailedmovie.movies

import com.example.moviebase.model.network.detailedmovie.DetailedMovieResult
import com.google.gson.annotations.SerializedName

data class DetailedMovieResult(
    override val id: Int?,
    override val popularity: Double?,
    @SerializedName("original_title")
    override val originalTitle: String?,
    @SerializedName("poster_path")
    override val posterPath: String?,
    @SerializedName("release_date")
    override val releaseDate: String?,
    @SerializedName("vote_average")
    override val voteAverage: Double?,
    @SerializedName("vote_count")
    override val voteCount: Int?
) : DetailedMovieResult