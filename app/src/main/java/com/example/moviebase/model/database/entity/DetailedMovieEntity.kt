package com.example.moviebase.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detailed_movies_table")
data class DetailedMovieEntity(
    @PrimaryKey(autoGenerate = false)
    override val id: Int,
    override val title: String,
    val overview: String,
    override val posterPath: String,
    override val releaseDate: String,
    override val voteAverage: Double,
    override val voteCount: Int
) : IMovieEntity