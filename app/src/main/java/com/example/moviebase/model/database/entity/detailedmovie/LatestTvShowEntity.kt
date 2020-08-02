package com.example.moviebase.model.database.entity.detailedmovie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviebase.model.database.entity.DetailedMovieEntity

@Entity(tableName = "latest_tv_show_table")
data class LatestTvShowEntity(
    @PrimaryKey(autoGenerate = false)
    override val id: Int,
    override val title: String,
    override val overview: String,
    override val posterPath: String,
    override val releaseDate: String,
    override val voteAverage: Double,
    override val voteCount: Int
) : DetailedMovieEntity