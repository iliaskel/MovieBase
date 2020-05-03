package com.example.moviebase.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviebase.model.database.MovieEnums.MovieType

@Entity(tableName = "movies_table")
data class MoviesEntity(
    @PrimaryKey(autoGenerate = false)
    private val id: Int,
    private val title: String,
    private val posterPath: String,
    private val releaseDate: String,
    private val voteAverage: Double,
    private val voteCount: Int,
    private val type: MovieType
)