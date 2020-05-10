package com.example.moviebase.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detailed_movies_table")
data class DetailedMovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int
)