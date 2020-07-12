package com.example.moviebase.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviebase.model.database.dao.*
import com.example.moviebase.model.database.entity.*

/**
 * An abstract [RoomDatabase] representation and the corresponding DAOs
 */
@Database(
    entities = [
        MoviesEntity::class,
        DetailedMovieEntity::class,
        ExtraMoviesEntity::class,
        TvShowsEntity::class,
        LatestTvShowEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TMDBDB : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun detailedMovieDao(): DetailedMovieDao
    abstract fun extraMoviesDao(): ExtraMoviesDao
    abstract fun tvShowsDao(): TvShowsDao
    abstract fun latestTvShowDao(): LatestTvShowDao
}