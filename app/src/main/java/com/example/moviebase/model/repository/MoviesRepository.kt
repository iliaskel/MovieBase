package com.example.moviebase.model.repository

import com.example.moviebase.model.database.entity.DetailedMovieEntity
import com.example.moviebase.model.database.entity.MovieType
import com.example.moviebase.model.database.entity.MoviesEntity
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    /**
     * Fetches all the movies with [MovieType] type and replaces the already
     * existing list (if exists) in the database.
     * After fetching the data, they are transformed
     * in a way to represent Objects of the database layer and then are stored.
     */
    suspend fun replaceMovies()

    /**
     * Fetches a movie with all the details needed and replaces the already
     * existing one (if exists) in the database. Except the details of the selected movie,
     * recommended and similar movies are also fetched.
     * After fetching the data, they are transformed in a way to represent Objects of the database layer
     * and then are stored.
     *
     * @param id an [Int] representing the movie's to be fetched id
     */
    suspend fun replaceDetailedMovie(id: String)

    /**
     * Deletes the [DetailedMovieEntity] stored in the database together with
     * the corresponding recommended and similar movies.
     */
    suspend fun deleteDetailedMovie()

    /**
     * Returns a list of movies with [MovieType] types as a [Flow]
     */
    fun getMovies(): Flow<List<MoviesEntity>>

    /**
     * Returns a detailed movie as a [Flow]
     */
    fun getDetailedMovie(): Flow<DetailedMovieEntity>

    /**
     * Returns similar and recommended movies ([ExtraMovieType]) for the [DetailedMovieEntity]
     */
    fun getExtraMovies(): Flow<List<MoviesEntity>>

}