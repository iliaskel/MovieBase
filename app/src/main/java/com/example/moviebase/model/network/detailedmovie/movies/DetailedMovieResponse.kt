package com.example.moviebase.model.network.detailedmovie.movies

import com.example.moviebase.model.network.detailedmovie.DetailedMovieResponse
import com.google.gson.annotations.SerializedName

data class DetailedMovieResponse(
    override val id: Int?,
    override val overview: String?,
    override val popularity: Double?,
    override val recommendations: DetailedMovieRecommendations?,
    override val similar: DetailedMovieSimilar?,
    override val status: String?,
    @SerializedName("poster_path")
    override val posterPath: String?,
    @SerializedName("release_date")
    override val releaseDate: String?,
    @SerializedName("original_title")
    override val originalTitle: String?,
    @SerializedName("vote_average")
    override val voteAverage: Double?,
    @SerializedName("vote_count")
    override val voteCount: Int?
) : DetailedMovieResponse