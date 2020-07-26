package com.example.moviebase.model.network.detailedmovie

import com.google.gson.annotations.SerializedName

data class DetailedMovieResponse(
    val id: Int,
    val overview: String,
    val popularity: Double,
    val recommendations: Recommendations,
    val similar: Similar,
    val status: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
) : IDetailedMovieResponse