package com.example.moviebase.model.database.dao

import androidx.room.*
import com.example.moviebase.model.database.entity.simplemovie.MoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * from movies_table")
    fun getMovies(): Flow<List<MoviesEntity>>

    @Transaction
    suspend fun replaceMovies(moviesList: List<MoviesEntity>) {
        deleteMovies()
        insertMovies(moviesList)
    }

    @Query("DELETE from movies_table")
    suspend fun deleteMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MoviesEntity>)

    @Transaction
    suspend fun replaceExtraMovies(extraMoviesList: List<MoviesEntity>) {
        deleteExtraMovies()
        insertExtraMovies(extraMoviesList)
    }

    @Query("DELETE from movies_table WHERE type = 4 OR type = 5")
    suspend fun deleteExtraMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExtraMovies(extraMovies: List<MoviesEntity>)

    @Query("SELECT * from movies_table WHERE type = 4 OR type = 5")
    fun getExtraMovies(): Flow<List<MoviesEntity>>
}