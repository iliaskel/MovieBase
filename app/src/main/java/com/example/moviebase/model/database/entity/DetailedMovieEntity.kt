package com.example.moviebase.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detailed_movies_table")
data class DetailedMovieEntity(
    @PrimaryKey(autoGenerate = false)
    private val id: Int,
    private val title: String,
    private val overview: String,
    private val posterPath: String,
    private val releaseDate: String,
    private val voteAverage: Double,
    private val voteCount: Int
)