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
    private val transformationUtils: MoviesTransformationsUtils
) : MoviesRepository {

    // region Fields

    private val moviesDao = tmdbDb.moviesDao()
    private val detailedMovieDao = tmdbDb.detailedMovieDao()

    // endregion

    // region Implements

    override suspend fun updateMovies() {
        withContext(Dispatchers.IO) {
            val popularMovies =
                getMappedMoviesAsync(
                    async { tmdbApiService.getPopularMovies() },
                    MovieType.POPULAR
                ).await()

            val topRatedMovies =
                getMappedMoviesAsync(
                    async { tmdbApiService.getTopRatedMovies() },
                    MovieType.TOP_RATED
                ).await()

            val playingNowMovies =
                getMappedMoviesAsync(
                    async { tmdbApiService.getPlayingNowMovies() },
                    MovieType.NOW_PLAYING
                ).await()

            val upcomingMovies =
                getMappedMoviesAsync(
                    async { tmdbApiService.getUpcomingMovies() },
                    MovieType.UPCOMING
                ).await()

            val moviesToWrite =
                transformationUtils.flatMovieLists(
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
            val detailedMovieResponse = tmdbApiService.getDetailedMovie(id)
            val similarMoviesResult =
                detailedMovieResponse.similar?.detailedMoviesResult
            val similarMovies = transformationUtils.toExtraMoviesEntities(
                similarMoviesResult,
                MovieType.EXTRA_MOVIES_SIMILAR
            )
            val recommendedMoviesResult =
                detailedMovieResponse.recommendations?.results
            val recommendedMovies = transformationUtils.toExtraMoviesEntities(
                recommendedMoviesResult,
                MovieType.EXTRA_MOVIES_RECOMMENDED
            )
            if (detailedMovieResponse.id != null) {
                val detailedMovieToWrite =
                    transformationUtils.toDetailedMovieEntity(detailedMovieResponse)
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