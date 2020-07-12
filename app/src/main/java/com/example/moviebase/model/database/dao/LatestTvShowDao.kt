package com.example.moviebase.model.database.dao

import androidx.room.*
import com.example.moviebase.model.database.entity.LatestTvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LatestTvShowDao {

    @Query("SELECT * from latest_tv_show_table LIMIT 1")
    fun getLatestTvShow(): Flow<LatestTvShowEntity>

    @Transaction
    suspend fun replaceLatestTvShow(detailedMovie: LatestTvShowEntity) {
        deleteLatestTvShow()
        insertLatestTvShow(detailedMovie)
    }

    @Query("DELETE from latest_tv_show_table")
    suspend fun deleteLatestTvShow()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLatestTvShow(detailedMovie: LatestTvShowEntity)
}