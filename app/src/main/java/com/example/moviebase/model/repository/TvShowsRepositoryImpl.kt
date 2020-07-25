package com.example.moviebase.model.repository

import com.example.moviebase.model.database.TMDBDB
import com.example.moviebase.model.database.entity.*
import com.example.moviebase.model.network.api.TMDBApiService
import com.example.moviebase.model.network.detailedtvshow.DetailedTvShowResponse
import com.example.moviebase.model.network.tvshows.TvShowResult
import com.example.moviebase.model.network.tvshows.TvShowsResponse
import com.example.moviebase.utils.IMAGES_BASE_URL
import com.example.moviebase.utils.IMAGE_SMALL_SIZE
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class TvShowsRepositoryImpl(
    tmdbDB: TMDBDB,
    private val tmdbApiService: TMDBApiService
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
            val latestMovie = latestTvShowDeferred.await().toLatestTvShowEntity()

            val tvShowsToWrite =
                getMoviesToWrite(
                    listOf(
                        popularMovies,
                        topRatedMovies,
                        playingNowMovies,
                        upcomingMovies
                    )
                )
            tvShowsDao.replaceTvShows(tvShowsToWrite)
            latestTvShowDao.replaceLatestTvShow(latestMovie)
        }
    }

    override suspend fun replaceDetailedTvShow(id: String) {
        withContext(Dispatchers.IO) {
            val detailedTvShow = tmdbApiService.getDetailedTvShow(id)
            val detailedTvShowToWrite = detailedTvShow.toDetailedTvShowEntity()

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

    // region Private Functions

    private fun getMoviesToWrite(listOfTvShowsLists: List<List<TvShowsEntity>>): List<TvShowsEntity> {
        val moviesToWrite = mutableListOf<TvShowsEntity>()
        for (movieType in listOfTvShowsLists) {
            for (movie in movieType) {
                moviesToWrite.add(movie)
            }
        }
        return moviesToWrite
    }

    private fun getExtraMoviesToWrite(listOfExtraMoviesList: List<List<MoviesEntity>>): List<MoviesEntity> {
        val extraMoviesToWrite = mutableListOf<MoviesEntity>()
        for (extraMovieType in listOfExtraMoviesList) {
            for (movie in extraMovieType) {
                extraMoviesToWrite.add(movie)
            }
        }
        return extraMoviesToWrite
    }

    // endregion

    // region Extension Functions

    private fun CoroutineScope.getMappedMoviesAsync(
        tvShowsDeferred: Deferred<TvShowsResponse>,
        movieType: TvShowType
    ): Deferred<List<TvShowsEntity>> {
        return async {
            tvShowsDeferred.await().results.toMappedMovies(movieType)
        }
    }

    private fun List<TvShowResult?>?.toMappedMovies(movieType: TvShowType): List<TvShowsEntity> {
        val tvShowsResult = this?.filterNotNull()
        if (tvShowsResult == null || tvShowsResult.isEmpty()) {
            return emptyList()
        }
        val movieEntities = mutableListOf<TvShowsEntity>()
        for (tvShow in tvShowsResult.iterator()) {
            movieEntities.add(tvShow.toTvShowEntity(movieType))
        }
        return movieEntities
    }

    private fun TvShowResult.toTvShowEntity(movieType: TvShowType): TvShowsEntity {
        return TvShowsEntity(
            id = this.id,
            posterPath = this.posterPath.constructSmallPosterPath(),
            releaseDate = this.releaseDate,
            title = this.originalTitle,
            type = movieType,
            voteAverage = this.voteAverage,
            voteCount = this.voteCount
        )
    }

    private fun DetailedTvShowResponse.toDetailedTvShowEntity(): DetailedMovieEntity {
        return DetailedMovieEntity(
            id = this.id,
            posterPath = this.posterPath.constructSmallPosterPath(),
            title = this.originalTitle,
            releaseDate = this.releaseDate,
            voteCount = this.voteCount,
            voteAverage = this.voteAverage,
            overview = this.overview
        )
    }

    private fun DetailedTvShowResponse.toLatestTvShowEntity(): LatestTvShowEntity {
        return LatestTvShowEntity(
            id = this.id,
            posterPath = this.posterPath.constructSmallPosterPath(),
            title = this.originalTitle,
            releaseDate = this.releaseDate,
            voteCount = this.voteCount,
            voteAverage = this.voteAverage,
            overview = this.overview
        )
    }


    private fun String.constructSmallPosterPath(): String {
        return IMAGES_BASE_URL.plus(IMAGE_SMALL_SIZE).plus(this)
    }

    private fun CoroutineScope.getMappedDetailedTvShowAsync(
        latestTvhShowDeferred: Deferred<DetailedTvShowResponse>
    ): Deferred<DetailedTvShowResponse> {
        return async {
            latestTvhShowDeferred.await()
        }
    }

    // end region

}