package com.example.moviebase.model.database.dao

import androidx.room.*
import com.example.moviebase.model.database.entity.TvShowsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowsDao {

    @Query("SELECT * FROM tv_shows_table")
    fun getTvShows(): Flow<List<TvShowsEntity>>

    @Transaction
    suspend fun replaceTvShows(moviesList: List<TvShowsEntity>) {
        deleteTvShows()
        insertTvShows(moviesList)
    }

    @Query("DELETE from tv_shows_table")
    suspend fun deleteTvShows()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(movies: List<TvShowsEntity>)
}