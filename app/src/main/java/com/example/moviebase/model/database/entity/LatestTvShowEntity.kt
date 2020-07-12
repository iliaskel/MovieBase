package com.example.moviebase.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "latest_tv_show_table")
data class LatestTvShowEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int
) : DetailedEntity