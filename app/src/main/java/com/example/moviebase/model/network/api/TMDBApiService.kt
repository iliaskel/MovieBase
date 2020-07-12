package com.example.moviebase.model.network.api

import com.example.moviebase.model.network.detailedmovie.DetailedMovieResponse
import com.example.moviebase.model.network.detailedtvshow.DetailedTvShowResponse
import com.example.moviebase.model.network.movies.PlayingNowResponse
import com.example.moviebase.model.network.movies.PopularResponse
import com.example.moviebase.model.network.movies.TopRatedResponse
import com.example.moviebase.model.network.movies.UpcomingResponse
import com.example.moviebase.model.network.tvshows.AiringNowTvShowsResponse
import com.example.moviebase.model.network.tvshows.PopularTvShowsResponse
import com.example.moviebase.model.network.tvshows.TopRatedTvShowsResponse
import com.example.moviebase.model.network.tvshows.UpcomingTvShowsResponse
import com.example.moviebase.utils.API_KEY_QUERY_PARAMETER
import com.example.moviebase.utils.APPEND_TO_RESPONSE_QUERY_PARAMETER
import com.example.moviebase.utils.PAGE_QUERY_PARAMETER
import com.example.moviebase.utils.SecretKey
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * An interface representing all the queries that the Application is using to interact
 * with the TMDB API.
 */
interface TMDBApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query(API_KEY_QUERY_PARAMETER) apiKey: String = SecretKey,
        @Query(PAGE_QUERY_PARAMETER) page: Int = 1
    ): PopularResponse

    @GET("movie/now_playing")
    suspend fun getPlayingNowMovies(
        @Query(API_KEY_QUERY_PARAMETER) apiKey: String = SecretKey,
        @Query(PAGE_QUERY_PARAMETER) page: Int = 1
    ): PlayingNowResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query(API_KEY_QUERY_PARAMETER) apiKey: String = SecretKey,
        @Query(PAGE_QUERY_PARAMETER) page: Int = 1
    ): TopRatedResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query(API_KEY_QUERY_PARAMETER) apiKey: String = SecretKey,
        @Query(PAGE_QUERY_PARAMETER) page: Int = 1
    ): UpcomingResponse

    @GET("movie/{movieId}")
    suspend fun getDetailedMovie(
        @Path("movieId") movieId: String,
        @Query(API_KEY_QUERY_PARAMETER) apiKey: String = SecretKey,
        @Query(APPEND_TO_RESPONSE_QUERY_PARAMETER) append: String = "recommendations,similar"
    ): DetailedMovieResponse

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query(API_KEY_QUERY_PARAMETER) apiKey: String = SecretKey,
        @Query(PAGE_QUERY_PARAMETER) page: Int = 1
    ): PopularTvShowsResponse

    @GET("tv/airing_today")
    suspend fun getAiringTodayTvShows(
        @Query(API_KEY_QUERY_PARAMETER) apiKey: String = SecretKey,
        @Query(PAGE_QUERY_PARAMETER) page: Int = 1
    ): AiringNowTvShowsResponse

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(
        @Query(API_KEY_QUERY_PARAMETER) apiKey: String = SecretKey,
        @Query(PAGE_QUERY_PARAMETER) page: Int = 1
    ): TopRatedTvShowsResponse

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTvShows(
        @Query(API_KEY_QUERY_PARAMETER) apiKey: String = SecretKey,
        @Query(PAGE_QUERY_PARAMETER) page: Int = 1
    ): UpcomingTvShowsResponse

    @GET("tv/{tv_id}")
    suspend fun getDetailedTvShow(
        @Path("movieId") movieId: String,
        @Query(API_KEY_QUERY_PARAMETER) apiKey: String = SecretKey,
        @Query(APPEND_TO_RESPONSE_QUERY_PARAMETER) append: String = "recommendations,similar"
    ): DetailedTvShowResponse

    @GET("tv/latest")
    suspend fun getLatestTvShow(
        @Query(API_KEY_QUERY_PARAMETER) apiKey: String = SecretKey
    ): DetailedTvShowResponse
}