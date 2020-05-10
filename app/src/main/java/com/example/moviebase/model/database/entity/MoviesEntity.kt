package com.example.moviebase.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters


@Entity(tableName = "movies_table")
@TypeConverters(MovieTypeConverters::class)
data class MoviesEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val type: MovieType
)

class MovieTypeConverters() {
    @TypeConverter
    fun fromIntegerToMovieType(value: Int): MovieType {
        return when (value) {
            0 -> MovieType.POPULAR
            1 -> MovieType.NOW_PLAYING
            2 -> MovieType.TOP_RATED
            3 -> MovieType.UPCOMING
            else -> MovieType.UNKNOWN
        }
    }

    @TypeConverter
    fun fromMovieTypeToInteger(movieType: MovieType): Int {
        return movieType.code
    }
}

enum class MovieType(val code: Int) {
    POPULAR(0),
    NOW_PLAYING(1),
    TOP_RATED(2),
    UPCOMING(3),
    UNKNOWN(-1)
}