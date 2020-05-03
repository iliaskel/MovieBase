package com.example.moviebase.model.database.dao

import androidx.room.*
import com.example.moviebase.model.database.entity.MoviesEntity
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
}