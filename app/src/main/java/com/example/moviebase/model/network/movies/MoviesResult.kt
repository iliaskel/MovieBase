package com.example.moviebase.model.network.movies

import com.google.gson.annotations.SerializedName

class MoviesResult(
    private val id: Int?,
    private val popularity: Double?,
    @SerializedName("original_title")
    private val originalTitle: String?,
    @SerializedName("poster_path")
    private val posterPath: String?,
    @SerializedName("release_date")
    private val releaseDate: String?,
    @SerializedName("vote_average")
    private val voteAverage: Double?,
    @SerializedName("vote_count")
    private val voteCount: Int?
)