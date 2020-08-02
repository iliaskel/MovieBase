package com.example.moviebase.model.utils

import com.example.moviebase.model.database.entity.detailedmovie.DetailedMovieEntity
import com.example.moviebase.model.database.entity.simplemovie.MovieType
import com.example.moviebase.model.database.entity.simplemovie.MoviesEntity
import com.example.moviebase.model.database.entity.simplemovie.TvShowsEntity
import com.example.moviebase.model.network.detailedmovie.DetailedMovieResult
import com.example.moviebase.model.network.detailedmovie.movies.DetailedMovieResponse
import com.example.moviebase.model.network.simplemovie.SimpleMovieResult
import com.example.moviebase.utils.IMAGES_BASE_URL
import com.example.moviebase.utils.IMAGE_BIG_SIZE
import com.example.moviebase.utils.IMAGE_SMALL_SIZE

class MoviesTransformationsUtils {

    fun toDetailedMovieEntity(detailedMovie: DetailedMovieResponse): DetailedMovieEntity? {
        return DetailedMovieEntity(
            id = detailedMovie.id!!,
            posterPath = detailedMovie.posterPath!!.constructSmallPosterPath(),
            title = detailedMovie.originalTitle!!,
            releaseDate = detailedMovie.releaseDate!!,
            voteCount = detailedMovie.voteCount!!,
            voteAverage = detailedMovie.voteAverage!!,
            overview = detailedMovie.overview!!
        )
    }

    fun toExtraMoviesEntities(
        detailedMovieResult: List<DetailedMovieResult?>?,
        movieType: MovieType
    ): List<MoviesEntity> {
        val extraMoviesList = mutableListOf<MoviesEntity>()
        detailedMovieResult.let {
            it?.filterNotNull()?.map { movie ->
                extraMoviesList.add(
                    MoviesEntity(
                        id = movie.id!!,
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

    private fun toMovieEntity(movieResult: SimpleMovieResult, movieType: MovieType): MoviesEntity? {
        return MoviesEntity(
            id = movieResult.id!!,
            posterPath = movieResult.posterPath!!.constructSmallPosterPath(),
            releaseDate = movieResult.releaseDate!!,
            title = movieResult.originalTitle!!,
            type = movieType,
            voteAverage = movieResult.voteAverage!!,
            voteCount = movieResult.voteCount!!
        )
    }

    fun toMappedMovies(
        moviesResult: List<SimpleMovieResult?>?,
        movieType: MovieType
    ): List<MoviesEntity> {
        val nonNullMoviesResult = moviesResult?.filterNotNull()
        if (nonNullMoviesResult == null || nonNullMoviesResult.isEmpty()) {
            return emptyList()
        }
        val movieEntities = mutableListOf<MoviesEntity>()
        for (movie in nonNullMoviesResult.iterator()) {
            toMovieEntity(movie, movieType)?.let {
                movieEntities.add(it)

            }
        }
        return movieEntities
    }

    fun getMoviesToWrite(listOfMovieLists: List<List<MoviesEntity>>): List<MoviesEntity> {
        val moviesToWrite = mutableListOf<MoviesEntity>()
        for (movieType in listOfMovieLists) {
            for (movie in movieType) {
                moviesToWrite.add(movie)
            }
        }
        return moviesToWrite
    }

    fun getTvShowsToWrite(listOfMovieLists: List<List<TvShowsEntity>>): List<TvShowsEntity> {
        val moviesToWrite = mutableListOf<TvShowsEntity>()
        for (movieType in listOfMovieLists) {
            for (movie in movieType) {
                moviesToWrite.add(movie)
            }
        }
        return moviesToWrite
    }

    fun getExtraMoviesToWrite(listOfExtraMoviesList: List<List<MoviesEntity>?>): List<MoviesEntity> {
        val extraMoviesToWrite = mutableListOf<MoviesEntity>()
        listOfExtraMoviesList.forEach { listOfExtraMovies ->
            listOfExtraMovies?.forEach { nonNullMovie ->
                extraMoviesToWrite.add(nonNullMovie)
            }
        }
        return extraMoviesToWrite
    }

    private fun String.constructSmallPosterPath(): String {
        return IMAGES_BASE_URL.plus(IMAGE_SMALL_SIZE).plus(this)
    }

    private fun String.constructBigPosterPath(): String {
        return IMAGES_BASE_URL.plus(IMAGE_BIG_SIZE).plus(this)
    }

}