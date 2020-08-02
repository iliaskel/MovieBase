package com.example.moviebase.model.network.detailedmovie.tvshows

import com.example.moviebase.model.network.detailedmovie.DetailedMovieResponse
import com.example.moviebase.model.network.detailedmovie.DetailedMovieSimilarMovies
import com.example.moviebase.model.network.detailedmovie.movies.DetailedMovieRecommendations
import com.google.gson.annotations.SerializedName

data class DetailedTvShowResponse(
    override val id: Int?,
    override val overview: String?,
    override val popularity: Double?,
    override val recommendations: DetailedMovieRecommendations?,
    override val similar: DetailedMovieSimilarMovies?,
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