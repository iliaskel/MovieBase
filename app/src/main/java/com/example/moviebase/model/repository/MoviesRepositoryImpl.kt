package com.example.moviebase.model.repository

import com.example.moviebase.model.database.TMDBDB
import com.example.moviebase.model.database.entity.*
import com.example.moviebase.model.network.api.TMDBApiService
import com.example.moviebase.model.network.detailedmovie.DetailedMovieResponse
import com.example.moviebase.model.network.detailedmovie.Result
import com.example.moviebase.model.network.movies.MoviesResponse
import com.example.moviebase.model.network.movies.MoviesResult
import com.example.moviebase.utils.IMAGES_BASE_URL
import com.example.moviebase.utils.IMAGE_BIG_SIZE
import com.example.moviebase.utils.IMAGE_SMALL_SIZE
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl(
    tmdbDb: TMDBDB,
    private val tmdbApiService: TMDBApiService
) : MoviesRepository {

    // region Fields

    private val moviesDao = tmdbDb.moviesDao()
    private val extraMoviesDao = tmdbDb.extraMoviesDao()
    private val detailedMovieDao = tmdbDb.detailedMovieDao()

    // endregion

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

    override suspend fun replaceDetailedMovie(id: String) {
        withContext(Dispatchers.IO) {
            val detailedMovie = tmdbApiService.getDetailedMovie(id)
            val similarMovies =
                detailedMovie.similar.results.toExtraMoviesEntities(ExtraMovieType.SIMILAR)
            val recommendedMovies =
                detailedMovie.recommendations.results.toExtraMoviesEntities(ExtraMovieType.RECOMMENDED)

            val detailedMovieToWrite = detailedMovie.toDetailedMovieEntity()
            val extraMoviesToWrite = getExtraMoviesToWrite(listOf(similarMovies, recommendedMovies))

            detailedMovieDao.replaceDetailedMovie(detailedMovieToWrite)
            extraMoviesDao.replaceExtraMovies(extraMoviesToWrite)
        }
    }

    override suspend fun deleteDetailedMovie() {
        withContext(Dispatchers.IO) {
            detailedMovieDao.deleteDetailedMovie()
            extraMoviesDao.deleteExtraMovies()
        }
    }

    override fun getMovies(): Flow<List<MoviesEntity>> {
        return moviesDao.getMovies()
    }

    override fun getDetailedMovie(): Flow<DetailedMovieEntity> {
        return detailedMovieDao.getDetailedMovie()
    }

    override fun getExtraMovies(): Flow<List<ExtraMoviesEntity>> {
        return extraMoviesDao.getExtraMovies()
    }

    // endregion

    // region Extension Functions

    private fun DetailedMovieResponse.toDetailedMovieEntity(): DetailedMovieEntity {
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

    private fun List<Result>.toExtraMoviesEntities(extraMovieType: ExtraMovieType): List<ExtraMoviesEntity> {
        val extraMoviesList = mutableListOf<ExtraMoviesEntity>()
        for (movie in this) {
            extraMoviesList.add(
                ExtraMoviesEntity(
                    id = movie.id,
                    title = movie.originalTitle,
                    posterPath = movie.posterPath.constructSmallPosterPath(),
                    voteAverage = movie.voteAverage,
                    voteCount = movie.voteCount,
                    releaseDate = movie.releaseDate,
                    extraMovieType = extraMovieType
                )
            )
        }
        return extraMoviesList
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
            movieEntities.add(movie.toMovieEntity(movieType))
        }
        return movieEntities
    }

    private fun MoviesResult.toMovieEntity(movieType: MovieType): MoviesEntity {
        return MoviesEntity(
            id = this.id,
            posterPath = this.posterPath.constructSmallPosterPath(),
            releaseDate = this.releaseDate,
            title = this.originalTitle,
            type = movieType,
            voteAverage = this.voteAverage,
            voteCount = this.voteCount
        )
    }

    private fun String.constructSmallPosterPath(): String {
        return IMAGES_BASE_URL.plus(IMAGE_SMALL_SIZE).plus(this)
    }

    private fun String.constructBigPosterPath(): String {
        return IMAGES_BASE_URL.plus(IMAGE_BIG_SIZE).plus(this)
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

    private fun getExtraMoviesToWrite(listOfExtraMoviesList: List<List<ExtraMoviesEntity>>): List<ExtraMoviesEntity> {
        val extraMoviesToWrite = mutableListOf<ExtraMoviesEntity>()
        for (extraMovieType in listOfExtraMoviesList) {
            for (movie in extraMovieType) {
                extraMoviesToWrite.add(movie)
            }
        }
        return extraMoviesToWrite
    }

    // endregion

}