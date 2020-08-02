package com.example.moviebase.model.repository

import com.example.moviebase.model.database.TMDBDB
import com.example.moviebase.model.database.entity.detailedmovie.DetailedMovieEntity
import com.example.moviebase.model.database.entity.simplemovie.MoviesEntity
import com.example.moviebase.model.database.entity.simplemovie.TvShowType
import com.example.moviebase.model.database.entity.simplemovie.TvShowsEntity
import com.example.moviebase.model.network.api.TMDBApiService
import com.example.moviebase.model.network.simplemovie.SimpleMovieResponse
import com.example.moviebase.model.utils.TvShowsTransformationsUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class TvShowsRepositoryImpl(
    tmdbDB: TMDBDB,
    private val tmdbApiService: TMDBApiService,
    private val tvShowsTransformationsUtils: TvShowsTransformationsUtils = TvShowsTransformationsUtils()
) : TvShowsRepository {

    // region Fields

    private val tvShowsDao = tmdbDB.tvShowsDao()
    private val latestTvShowDao = tmdbDB.latestTvShowDao()
    private val detailedMovieDao = tmdbDB.detailedMovieDao()

    // endregion

    // region Overrides

    override suspend fun replaceTvShows() {
        withContext(Dispatchers.IO) {
            val popularTvShowsDeferred = async { tmdbApiService.getPopularTvShows() }
            val topRatedMoviesDeferred = async { tmdbApiService.getAiringTodayTvShows() }
            val playingNowMoviesDeferred = async { tmdbApiService.getTopRatedTvShows() }
            val upcomingMoviesDeferred = async { tmdbApiService.getOnTheAirTvShows() }
            val latestTvShowDeferred = async { tmdbApiService.getLatestTvShow() }

            val popularMovies =
                getMappedMoviesAsync(popularTvShowsDeferred, TvShowType.POPULAR).await()
            val topRatedMovies =
                getMappedMoviesAsync(topRatedMoviesDeferred, TvShowType.TOP_RATED).await()
            val playingNowMovies =
                getMappedMoviesAsync(playingNowMoviesDeferred, TvShowType.AIRING_NOW).await()
            val upcomingMovies =
                getMappedMoviesAsync(upcomingMoviesDeferred, TvShowType.UPCOMING).await()
            val latestMovieResponse = latestTvShowDeferred.await()
            val latestMovie = tvShowsTransformationsUtils.toLatestTvShowEntity(latestMovieResponse)

            val tvShowsToWrite =
                tvShowsTransformationsUtils.getTvShowsToWrite(
                    listOf(
                        popularMovies,
                        topRatedMovies,
                        playingNowMovies,
                        upcomingMovies
                    )
                )
            tvShowsDao.replaceTvShows(tvShowsToWrite)
            if (latestMovie != null) {
                latestTvShowDao.replaceLatestTvShow(latestMovie)

            }
        }
    }

    override suspend fun replaceDetailedTvShow(id: String) {
        withContext(Dispatchers.IO) {
            val detailedTvShow = tmdbApiService.getDetailedTvShow(id)
            val detailedTvShowToWrite =
                tvShowsTransformationsUtils.toDetailedTvShowEntity(detailedTvShow)

            detailedMovieDao.replaceDetailedMovie(detailedTvShowToWrite)
        }
    }

    override suspend fun deleteDetailedTvShow() {
        withContext(Dispatchers.IO) {
            detailedMovieDao.deleteDetailedMovie()
        }
    }

    override fun getTvShows(): Flow<List<TvShowsEntity>> {
        return tvShowsDao.getTvShows()
    }

    override fun getDetailedMovie(): Flow<DetailedMovieEntity> {
        return detailedMovieDao.getDetailedMovie()
    }

    override fun getExtraMovies(): Flow<List<MoviesEntity>> {
        TODO("Not yet implemented")
    }

    // end region

    // region Extension Functions

    private fun CoroutineScope.getMappedMoviesAsync(
        tvShowsDeferred: Deferred<SimpleMovieResponse>,
        movieType: TvShowType
    ): Deferred<List<TvShowsEntity>> {
        return async {
            val tvShowsResults = tvShowsDeferred.await().results
            return@async tvShowsTransformationsUtils.toMappedTvShows(tvShowsResults, movieType)
        }
    }

    // end region

}