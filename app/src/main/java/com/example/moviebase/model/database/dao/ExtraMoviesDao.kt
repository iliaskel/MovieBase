package com.example.moviebase.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.moviebase.model.database.entity.ExtraMoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExtraMoviesDao {

    @Query("SELECT * from extra_movies_table")
    fun getExtraMovies(): Flow<List<ExtraMoviesEntity>>

    @Transaction
    suspend fun replaceExtraMovies(extraMovies: List<ExtraMoviesEntity>) {
        deleteExtraMovies()
        insertExtraMovies(extraMovies)
    }

    @Query("DELETE from extra_movies_table")
    suspend fun deleteExtraMovies()

    @Insert
    suspend fun insertExtraMovies(extraMovies: List<ExtraMoviesEntity>)
}