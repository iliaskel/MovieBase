package com.example.moviebase.model.network.searchmovie

import com.google.gson.annotations.SerializedName

data class SearchMovieResult(
    val id: Int?,
    val overview: String?,
    val popularity: Double?,
    val title: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
)