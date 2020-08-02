package com.example.moviebase.model.database.dao

import androidx.room.*
import com.example.moviebase.model.database.entity.DetailedMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DetailedMovieDao {

    @Query("SELECT * from detailed_movies_table LIMIT 1")
    fun getDetailedMovie(): Flow<DetailedMovieEntity>

    @Transaction
    suspend fun replaceDetailedMovie(detailedMovie: DetailedMovieEntity?) {
        deleteDetailedMovie()
        insertDetailedMovie(detailedMovie)
    }

    @Query("DELETE from detailed_movies_table")
    suspend fun deleteDetailedMovie()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailedMovie(detailedMovie: DetailedMovieEntity?)
}