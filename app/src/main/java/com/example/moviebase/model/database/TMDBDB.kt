package com.example.moviebase.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviebase.model.database.dao.DetailedMovieDao
import com.example.moviebase.model.database.dao.LatestTvShowDao
import com.example.moviebase.model.database.dao.MoviesDao
import com.example.moviebase.model.database.dao.TvShowsDao
import com.example.moviebase.model.database.entity.detailedmovie.DetailedMovieEntity
import com.example.moviebase.model.database.entity.detailedmovie.LatestTvShowEntity
import com.example.moviebase.model.database.entity.simplemovie.MoviesEntity
import com.example.moviebase.model.database.entity.simplemovie.TvShowsEntity

/**
 * An abstract [RoomDatabase] representation and the corresponding DAOs
 */
@Database(
    entities = [
        MoviesEntity::class,
        DetailedMovieEntity::class,
        TvShowsEntity::class,
        LatestTvShowEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TMDBDB : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun detailedMovieDao(): DetailedMovieDao
    abstract fun tvShowsDao(): TvShowsDao
    abstract fun latestTvShowDao(): LatestTvShowDao
}