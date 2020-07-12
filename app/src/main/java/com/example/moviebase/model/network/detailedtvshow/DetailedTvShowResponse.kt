package com.example.moviebase.model.network.detailedtvshow

import com.example.moviebase.model.network.Response
import com.google.gson.annotations.SerializedName

data class DetailedTvShowResponse(
    val id: Int,
    val overview: String,
    val popularity: Double,
    val recommendations: TvShowsRecommendations,
    val similar: TvShowsSimilar,
    val status: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("originanl_title")
    val originalTitle: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
) : Response