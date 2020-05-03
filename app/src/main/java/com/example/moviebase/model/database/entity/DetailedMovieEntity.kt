package com.example.moviebase.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviebase.model.database.MovieEnums

@Entity(tableName = "detailed_movies_table")
data class DetailedMovieEntity(
    @PrimaryKey(autoGenerate = false)
    private val id: Int,
    private val title: String,
    private val overview: String,
    private val posterPath: String,
    private val type: MovieEnums.MovieType,
    private val releaseDate: String,
    private val voteAverage: Double,
    private val voteCount: Int,
    private val extraMovies: List<ExtraMoviesEntity>
)