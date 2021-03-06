package com.example.moviebase.model.network.tvshows

import com.google.gson.annotations.SerializedName

data class TvShowResult(
    val id: Int,
    val popularity: Double,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)