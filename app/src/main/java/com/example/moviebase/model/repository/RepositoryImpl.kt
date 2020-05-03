package com.example.moviebase.model.repository

import com.example.moviebase.model.database.MovieEnums.MovieType
import com.example.moviebase.model.database.TMDBDB
import com.example.moviebase.model.database.entity.DetailedMovieEntity
import com.example.moviebase.model.database.entity.ExtraMoviesEntity
import com.example.moviebase.model.database.entity.MoviesEntity
import com.example.moviebase.model.network.api.TMDBApiService
import com.example.moviebase.model.network.movies.MoviesResponse
import com.example.moviebase.model.network.movies.MoviesResult
import com.example.moviebase.utils.IMAGES_BASE_URL
import com.example.moviebase.utils.IMAGE_SMALL_SIZE
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(
    tmdbDb: TMDBDB,
    private val tmdbApiService: TMDBApiService
) : Repository {

    private val moviesDao = tmdbDb.moviesDao()
    private val extraMoviesDao = tmdbDb.extraMoviesDao()
    private val detailedMovieDao = tmdbDb.detailedMovieDao()

    // region Implements

    override suspend fun replaceMovies() {
        withContext(Dispatchers.IO) {
            val popularMoviesDeferred = async { tmdbApiService.getPopularMovies() }
            val topRatedMoviesDeferred = async { tmdbApiService.getTopRatedMovies() }
            val playingNowMoviesDeferred = async { tmdbApiService.getPlayingNowMovies() }
            val upcomingMoviesDeferred = async { tmdbApiService.getUpcomingMovies() }

            val popularMovies =
                getMappedMoviesAsync(popularMoviesDeferred, MovieType.POPULAR).await()
            val topRatedMovies =
                getMappedMoviesAsync(topRatedMoviesDeferred, MovieType.TOP_RATED).await()
            val playingNowMovies =
                getMappedMoviesAsync(playingNowMoviesDeferred, MovieType.NOW_PLAYING).await()
            val upcomingMovies =
                getMappedMoviesAsync(upcomingMoviesDeferred, MovieType.UPCOMING).await()

            val moviesToWrite =
                getMoviesToWrite(
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

    override suspend fun replaceDetailedMovie(id: Int) {
    }

    override fun getMovies(): Flow<List<MoviesEntity>> {
    }

    override fun getDetailedMovie(): Flow<DetailedMovieEntity> {
    }

    override fun getExtraMovies(): Flow<List<ExtraMoviesEntity>> {
    }

    // endregion

    // region Private Functions

    private fun getMoviesToWrite(listOfMovieLists: List<List<MoviesEntity>>): List<MoviesEntity> {
        val moviesToWrite = mutableListOf<MoviesEntity>()
        for (movieType in listOfMovieLists) {
            for (movie in movieType) {
                moviesToWrite.add(movie)
            }
        }
        return moviesToWrite
    }

    private fun CoroutineScope.getMappedMoviesAsync(
        moviesDeferred: Deferred<MoviesResponse>,
        movieType: MovieType
    ): Deferred<List<MoviesEntity>> {
        return async {
            moviesDeferred.await().results.toMappedMovies(movieType)
        }
    }

    private fun List<MoviesResult?>?.toMappedMovies(movieType: MovieType): List<MoviesEntity> {
        val moviesResult = this?.filterNotNull()
        if (moviesResult == null || moviesResult.isEmpty()) {
            return emptyList()
        }
        val movieEntities = mutableListOf<MoviesEntity>()
        for (movie in moviesResult.iterator()) {
            movieEntities.add(movie.mapMovie(movieType))
        }
        return movieEntities
    }

    private fun MoviesResult.mapMovie(movieType: MovieType): MoviesEntity {
        return MoviesEntity(
            id = this.id,
            posterPath = this.posterPath.constructPosterPath(),
            releaseDate = this.releaseDate,
            title = this.originalTitle,
            type = movieType,
            voteAverage = this.voteAverage,
            voteCount = this.voteCount
        )
    }

    private fun String.constructPosterPath(): String {
        return IMAGES_BASE_URL.plus(IMAGE_SMALL_SIZE).plus(this)
    }

    // endregion

}