package com.example.moviebase.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "latest_tv_show_table")
data class LatestTvShowEntity(
    @PrimaryKey(autoGenerate = false)
    override val id: Int,
    override val title: String,
    val overview: String,
    override val posterPath: String,
    override val releaseDate: String,
    override val voteAverage: Double,
    override val voteCount: Int
) : IMovieEntity