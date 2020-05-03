package com.example.moviebase.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviebase.model.database.MovieEnums.ExtraType

@Entity(tableName = "extra_movies_table")
data class ExtraMoviesEntity(
    @PrimaryKey(autoGenerate = false)
    private val id: Int,
    private val title: String,
    private val posterPath: String,
    private val releaseDate: String,
    private val voteAverage: Double,
    private val voteCount: Int,
    private val extraType: ExtraType
)