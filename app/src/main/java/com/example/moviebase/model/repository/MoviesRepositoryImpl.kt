package com.example.moviebase.model.repository

import com.example.moviebase.model.database.TMDBDB
import com.example.moviebase.model.database.entity.DetailedMovieEntity
import com.example.moviebase.model.database.entity.MovieType
import com.example.moviebase.model.database.entity.MoviesEntity
import com.example.moviebase.model.network.api.TMDBApiService
import com.example.moviebase.model.network.detailedmovie.DetailedMovieResponse
import com.example.moviebase.model.network.detailedmovie.Result
import com.example.moviebase.model.network.movies.IMoviesResponse
import com.example.moviebase.model.network.movies.MoviesResult
import com.example.moviebase.model.utils.ObjectUtils
import com.example.moviebase.utils.IMAGES_BASE_URL
import com.example.moviebase.utils.IMAGE_BIG_SIZE
import com.example.moviebase.utils.IMAGE_SMALL_SIZE
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl(
    tmdbDb: TMDBDB,
    private val tmdbApiService: TMDBApiService,
    private val objectUtils: ObjectUtils = ObjectUtils()
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

    override suspend fun updateDetailedMovie(id: String) {
        withContext(Dispatchers.IO) {
            val detailedMovie = tmdbApiService.getDetailedMovie(id)
            val similarMovies =
                detailedMovie.similar?.results?.toExtraMoviesEntities(MovieType.EXTRA_MOVIES_SIMILAR)
            val recommendedMovies =
                detailedMovie.recommendations?.results?.toExtraMoviesEntities(MovieType.EXTRA_MOVIES_RECOMMENDED)

            val detailedMovieToWrite = detailedMovie.toDetailedMovieEntity()
            val extraMoviesToWrite = getExtraMoviesToWrite(listOf(similarMovies, recommendedMovies))

            detailedMovieDao.replaceDetailedMovie(detailedMovieToWrite)
            moviesDao.replaceExtraMovies(extraMoviesToWrite)
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

    private fun DetailedMovieResponse.toDetailedMovieEntity(): DetailedMovieEntity? {
        if (objectUtils.hasNullField(this)) return null
        return DetailedMovieEntity(
            id = this.id!!,
            posterPath = this.posterPath!!.constructSmallPosterPath(),
            title = this.originalTitle!!,
            releaseDate = this.releaseDate!!,
            voteCount = this.voteCount!!,
            voteAverage = this.voteAverage!!,
            overview = this.overview!!
        )
    }

    private fun List<Result?>?.toExtraMoviesEntities(movieType: MovieType): List<MoviesEntity> {
        val extraMoviesList = mutableListOf<MoviesEntity>()
        this?.let {
            this.filter {
                it != null && !objectUtils.hasNullField(it)
            }.map { movie ->
                extraMoviesList.add(
                    MoviesEntity(
                        id = movie!!.id!!,
                        title = movie.originalTitle!!,
                        posterPath = movie.posterPath!!.constructSmallPosterPath(),
                        voteAverage = movie.voteAverage!!,
                        voteCount = movie.voteCount!!,
                        releaseDate = movie.releaseDate!!,
                        type = movieType
                    )
                )
            }
        }
        return extraMoviesList
    }

    private fun MoviesResult.toMovieEntity(movieType: MovieType): MoviesEntity? {
        if (objectUtils.hasNullField(this)) return null
        return MoviesEntity(
            id = this.id!!,
            posterPath = this.posterPath!!.constructSmallPosterPath(),
            releaseDate = this.releaseDate!!,
            title = this.originalTitle!!,
            type = movieType,
            voteAverage = this.voteAverage!!,
            voteCount = this.voteCount!!
        )
    }

    private fun String.constructSmallPosterPath(): String {
        return IMAGES_BASE_URL.plus(IMAGE_SMALL_SIZE).plus(this)
    }

    private fun String.constructBigPosterPath(): String {
        return IMAGES_BASE_URL.plus(IMAGE_BIG_SIZE).plus(this)
    }

    private fun CoroutineScope.getMappedMoviesAsync(
        moviesDeferred: Deferred<IMoviesResponse>,
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
            movie.toMovieEntity(movieType)?.let {
                movieEntities.add(it)
            }
        }
        return movieEntities
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

    private fun getExtraMoviesToWrite(listOfExtraMoviesList: List<List<MoviesEntity>?>): List<MoviesEntity> {
        val extraMoviesToWrite = mutableListOf<MoviesEntity>()
        listOfExtraMoviesList.forEach { listOfExtraMovies ->
            listOfExtraMovies?.filter { movie ->
                !objectUtils.hasNullField(movie)
            }?.forEach { nonNullMovie ->
                extraMoviesToWrite.add(nonNullMovie)
            }
        }
        return extraMoviesToWrite
    }

// endregion

}