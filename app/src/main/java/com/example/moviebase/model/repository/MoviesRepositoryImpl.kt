package com.example.moviebase.model.repository

import com.example.moviebase.model.database.TMDBDB
import com.example.moviebase.model.database.entity.detailedmovie.DetailedMovieEntity
import com.example.moviebase.model.database.entity.simplemovie.MovieType
import com.example.moviebase.model.database.entity.simplemovie.MoviesEntity
import com.example.moviebase.model.network.api.TMDBApiService
import com.example.moviebase.model.network.simplemovie.SimpleMovieResponse
import com.example.moviebase.model.utils.MoviesTransformationsUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl(
    tmdbDb: TMDBDB,
    private val tmdbApiService: TMDBApiService,
    private val transformationUtils: MoviesTransformationsUtils = MoviesTransformationsUtils()
) : MoviesRepository {

    // region Fields

    private val moviesDao = tmdbDb.moviesDao()
    private val detailedMovieDao = tmdbDb.detailedMovieDao()

    // endregion

    // region Implements

    override suspend fun updateMovies() {
        withContext(Dispatchers.IO) {
            val popularMoviesDeferred = async { tmdbApiService.getPopularMovies() }
            val popularMovies =
                getMappedMoviesAsync(popularMoviesDeferred, MovieType.POPULAR).await()

            val topRatedMoviesDeferred = async { tmdbApiService.getTopRatedMovies() }
            val topRatedMovies =
                getMappedMoviesAsync(topRatedMoviesDeferred, MovieType.TOP_RATED).await()

            val playingNowMoviesDeferred = async { tmdbApiService.getPlayingNowMovies() }
            val playingNowMovies =
                getMappedMoviesAsync(playingNowMoviesDeferred, MovieType.NOW_PLAYING).await()

            val upcomingMoviesDeferred = async { tmdbApiService.getUpcomingMovies() }
            val upcomingMovies =
                getMappedMoviesAsync(upcomingMoviesDeferred, MovieType.UPCOMING).await()

            val moviesToWrite =
                transformationUtils.getMoviesToWrite(
                    listOf(
                        popularMovies,
                        topRatedMovies,
                        playingNowMovies,
                        upcomingMovies
                    )
                )
            moviesDao.replaceMovies(moviesToWrite)
        }
    }

    override suspend fun updateDetailedMovie(id: String) {
        withContext(Dispatchers.IO) {
            val detailedMovie = tmdbApiService.getDetailedMovie(id)
            val similarMoviesResult =
                detailedMovie.similar?.detailedMoviesResult
            val similarMovies = transformationUtils.toExtraMoviesEntities(
                similarMoviesResult,
                MovieType.EXTRA_MOVIES_SIMILAR
            )
            val recommendedMoviesResult =
                detailedMovie.recommendations?.results
            val recommendedMovies = transformationUtils.toExtraMoviesEntities(
                recommendedMoviesResult,
                MovieType.EXTRA_MOVIES_RECOMMENDED
            )
            if (detailedMovie.id != null) {
                val detailedMovieToWrite = transformationUtils.toDetailedMovieEntity(detailedMovie)
                val extraMoviesToWrite =
                    transformationUtils.getExtraMoviesToWrite(
                        listOf(
                            similarMovies,
                            recommendedMovies
                        )
                    )

                detailedMovieDao.replaceDetailedMovie(detailedMovieToWrite)
                moviesDao.replaceExtraMovies(extraMoviesToWrite)
            }

        }
    }

    override suspend fun deleteDetailedMovie() {
        withContext(Dispatchers.IO) {
            detailedMovieDao.deleteDetailedMovie()
            moviesDao.deleteExtraMovies()
        }
    }

    override fun getMovies(): Flow<List<MoviesEntity>> {
        return moviesDao.getMovies()
    }

    override fun getDetailedMovie(): Flow<DetailedMovieEntity> {
        return detailedMovieDao.getDetailedMovie()
    }

    override fun getExtraMovies(): Flow<List<MoviesEntity>> {
        return moviesDao.getExtraMovies()
    }

    // endregion

    // region Extension Functions

    private fun CoroutineScope.getMappedMoviesAsync(
        moviesDeferred: Deferred<SimpleMovieResponse>,
        movieType: MovieType
    ): Deferred<List<MoviesEntity>> {
        return async {
            val movieResults = moviesDeferred.await().results
            return@async transformationUtils.toMappedMovies(movieResults, movieType)
        }
    }

    // endregion

}