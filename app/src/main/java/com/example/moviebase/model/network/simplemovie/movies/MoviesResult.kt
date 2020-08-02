package com.example.moviebase.model.network.simplemovie.movies

import com.example.moviebase.model.network.simplemovie.SimpleMovieResult
import com.google.gson.annotations.SerializedName

data class MoviesResult(
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
) : SimpleMovieResult