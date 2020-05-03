package com.example.moviebase.model.network.detailedmovie

import com.google.gson.annotations.SerializedName

data class DetailedMovieResponse(
    val credits: Credits?,
    val id: Int?,
    val overview: String?,
    val popularity: Double?,
    val recommendations: Recommendations?,
    val runtime: Int?,
    val status: String?,
    val title: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("similar_movies")
    val similarMovies: SimilarMovies?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
)