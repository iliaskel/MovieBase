package com.example.moviebase.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters


@Entity(tableName = "movies_table")
@TypeConverters(MovieTypeConverters::class)
data class MoviesEntity(
    @PrimaryKey(autoGenerate = false)
    override val id: Int,
    override val title: String,
    override val posterPath: String,
    override val releaseDate: String,
    override val voteAverage: Double,
    override val voteCount: Int,
    val type: MovieType
) : IMovieEntity

class MovieTypeConverters() {
    @TypeConverter
    fun fromIntegerToMovieType(value: Int): MovieType {
        return when (value) {
            0 -> MovieType.POPULAR
            1 -> MovieType.NOW_PLAYING
            2 -> MovieType.TOP_RATED
            3 -> MovieType.UPCOMING
            4 -> MovieType.EXTRA_MOVIES_RECOMMENDED
            5 -> MovieType.EXTRA_MOVIES_SIMILAR
            else -> MovieType.UNKNOWN
        }
    }

    @TypeConverter
    fun fromMovieTypeToInteger(movieType: MovieType): Int {
        return movieType.code
    }

    @TypeConverter
    fun fromMovieTypeToTitle(movieType: MovieType): String {
        return movieType.title
    }
}

enum class MovieType(val code: Int, val title: String) {
    POPULAR(0, "Popular"),
    NOW_PLAYING(1, "Now Playing"),
    TOP_RATED(2, "Top Rated"),
    UPCOMING(3, "Upcoming"),
    EXTRA_MOVIES_RECOMMENDED(4, "Recommended"),
    EXTRA_MOVIES_SIMILAR(5, "Similar"),
    UNKNOWN(-1, "")
}