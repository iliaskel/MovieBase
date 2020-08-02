package com.example.moviebase.model.utils

import com.example.moviebase.model.database.entity.detailedmovie.DetailedMovieEntity
import com.example.moviebase.model.database.entity.detailedmovie.LatestTvShowEntity
import com.example.moviebase.model.database.entity.simplemovie.TvShowType
import com.example.moviebase.model.database.entity.simplemovie.TvShowsEntity
import com.example.moviebase.model.network.detailedmovie.tvshows.DetailedTvShowResponse
import com.example.moviebase.model.network.simplemovie.SimpleMovieResult
import com.example.moviebase.utils.IMAGES_BASE_URL
import com.example.moviebase.utils.IMAGE_BIG_SIZE
import com.example.moviebase.utils.IMAGE_SMALL_SIZE

class TvShowsTransformationsUtils {

    fun toLatestTvShowEntity(detailedTvShowResponse: DetailedTvShowResponse): LatestTvShowEntity? {
        return if (
            detailedTvShowResponse.id == null || detailedTvShowResponse.posterPath == null ||
            detailedTvShowResponse.originalTitle == null || detailedTvShowResponse.releaseDate == null ||
            detailedTvShowResponse.voteCount == null || detailedTvShowResponse.voteAverage == null || detailedTvShowResponse.overview == null
        )
            null
        else
            LatestTvShowEntity(
                id = detailedTvShowResponse.id,
                posterPath = detailedTvShowResponse.posterPath.constructSmallPosterPath(),
                title = detailedTvShowResponse.originalTitle,
                releaseDate = detailedTvShowResponse.releaseDate,
                voteCount = detailedTvShowResponse.voteCount,
                voteAverage = detailedTvShowResponse.voteAverage,
                overview = detailedTvShowResponse.overview
            )
    }

    fun getTvShowsToWrite(listOfTvShowsLists: List<List<TvShowsEntity>>): List<TvShowsEntity> {
        val moviesToWrite = mutableListOf<TvShowsEntity>()
        for (movieType in listOfTvShowsLists) {
            for (movie in movieType) {
                moviesToWrite.add(movie)
            }
        }
        return moviesToWrite
    }

    fun toDetailedTvShowEntity(detailedTvShowResponse: DetailedTvShowResponse): DetailedMovieEntity? {
        return if (detailedTvShowResponse.id == null || detailedTvShowResponse.originalTitle == null ||
            detailedTvShowResponse.posterPath == null || detailedTvShowResponse.voteAverage == null ||
            detailedTvShowResponse.releaseDate == null || detailedTvShowResponse.overview == null ||
            detailedTvShowResponse.voteCount == null
        ) null
        else
            DetailedMovieEntity(
                id = detailedTvShowResponse.id,
                posterPath = detailedTvShowResponse.posterPath.constructBigPosterPath(),
                title = detailedTvShowResponse.originalTitle,
                releaseDate = detailedTvShowResponse.releaseDate,
                voteCount = detailedTvShowResponse.voteCount,
                voteAverage = detailedTvShowResponse.voteAverage,
                overview = detailedTvShowResponse.overview
            )
    }

    fun toMappedTvShows(
        tvShowsResults: List<SimpleMovieResult?>?,
        movieType: TvShowType
    ): List<TvShowsEntity> {
        val tvShowsResult = tvShowsResults?.filterNotNull()
        if (tvShowsResult == null || tvShowsResult.isEmpty()) {
            return emptyList()
        }
        val movieEntities = mutableListOf<TvShowsEntity?>()
        for (tvShow in tvShowsResult.iterator()) {
            movieEntities.add(toTvShowEntity(tvShow, movieType))
        }
        return movieEntities.filterNotNull()
    }

    private fun toTvShowEntity(
        tvShowResult: SimpleMovieResult,
        movieType: TvShowType
    ): TvShowsEntity? {
        return if (tvShowResult.id == null || tvShowResult.originalTitle == null ||
            tvShowResult.posterPath == null || tvShowResult.voteAverage == null ||
            tvShowResult.releaseDate == null ||
            tvShowResult.voteCount == null
        ) null
        else
            TvShowsEntity(
                id = tvShowResult.id!!,
                posterPath = tvShowResult.posterPath!!.constructBigPosterPath(),
                releaseDate = tvShowResult.releaseDate!!,
                title = tvShowResult.originalTitle!!,
                type = movieType,
                voteAverage = tvShowResult.voteAverage!!,
                voteCount = tvShowResult.voteCount!!
            )
    }

    private fun String.constructSmallPosterPath(): String {
        return IMAGES_BASE_URL.plus(IMAGE_SMALL_SIZE).plus(this)
    }

    private fun String.constructBigPosterPath(): String {
        return IMAGES_BASE_URL.plus(IMAGE_BIG_SIZE).plus(this)
    }

}