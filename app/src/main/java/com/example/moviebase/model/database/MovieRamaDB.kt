package com.example.moviebase.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviebase.model.database.dao.DetailedMovieDao
import com.example.moviebase.model.database.dao.ExtraMoviesDao
import com.example.moviebase.model.database.dao.MoviesDao
import com.example.moviebase.model.database.entity.DetailedMovieEntity
import com.example.moviebase.model.database.entity.ExtraMoviesEntity
import com.example.moviebase.model.database.entity.MoviesEntity

/**
 * An abstract [RoomDatabase] representation and the corresponding DAOs
 */
@Database(
    entities = [
        MoviesEntity::class,
        DetailedMovieEntity::class,
        ExtraMoviesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SquadDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun detailedMovieDao(): DetailedMovieDao
    abstract fun extraMoviesDao(): ExtraMoviesDao
}