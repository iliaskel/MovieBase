package com.example.moviebase.model.repository

import com.example.moviebase.model.database.entity.DetailedMovieEntity
import com.example.moviebase.model.database.entity.MoviesEntity
import com.example.moviebase.model.database.entity.TvShowType
import com.example.moviebase.model.database.entity.TvShowsEntity
import kotlinx.coroutines.flow.Flow

interface TvShowsRepository {
    /**
     * Fetches all the tv shows with [TvShowType] type and replaces the already
     * existing list (if exists) in the database.
     * After fetching the data, they are transformed
     * in a way to represent Objects of the database layer and then are stored.
     */
    suspend fun replaceTvShows()

    /**
     * Fetches a tv show with all the details needed and replaces the already
     * existing one (if exists) in the database. Except the details of the selected tv show,
     * recommended and similar movies are also fetched.
     * After fetching the data, they are transformed in a way to represent Objects of the database layer
     * and then are stored.
     *
     * @param id an [Int] representing the tv show to be fetched id
     */
    suspend fun replaceDetailedTvShow(id: String)

    /**
     * Deletes the [DetailedMovieEntity] stored in the database together with
     * the corresponding recommended and similar movies.
     */
    suspend fun deleteDetailedTvShow()

    /**
     * Returns a list of movies with [TvShowType] types as a [Flow]
     */
    fun getTvShows(): Flow<List<TvShowsEntity>>

    /**
     * Returns a detailed movie as a [Flow]
     */
    fun getDetailedMovie(): Flow<DetailedMovieEntity?>

    /**
     * Returns similar and recommended movies ([ExtraMovieType]) for the [DetailedMovieEntity]
     */
    fun getExtraMovies(): Flow<List<MoviesEntity>>
}