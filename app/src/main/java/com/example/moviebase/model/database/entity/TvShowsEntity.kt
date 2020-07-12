package com.example.moviebase.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(tableName = "tv_shows_table")
@TypeConverters(TvShowsTypeConverters::class)
data class TvShowsEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val type: TvShowType
) : DetailedEntity

class TvShowsTypeConverters {
    @TypeConverter
    fun fromIntegerToTvShowTypeType(value: Int): TvShowType {
        return when (value) {
            0 -> TvShowType.POPULAR
            1 -> TvShowType.AIRING_NOW
            2 -> TvShowType.TOP_RATED
            3 -> TvShowType.UPCOMING
            4 -> TvShowType.LATEST
            else -> TvShowType.UNKNOWN
        }
    }

    @TypeConverter
    fun fromTvShowTypeToInteger(movieType: TvShowType): Int {
        return movieType.code
    }

    @TypeConverter
    fun fromTvShowTypeToTitle(movieType: TvShowType): String {
        return movieType.title
    }
}


enum class TvShowType(val code: Int, val title: String) {
    POPULAR(0, "Popular"),
    AIRING_NOW(1, "Now Playing"),
    TOP_RATED(2, "Top Rated"),
    UPCOMING(3, "Upcoming"),
    LATEST(4, "Latest Tv Show"),
    UNKNOWN(-1, "")
}