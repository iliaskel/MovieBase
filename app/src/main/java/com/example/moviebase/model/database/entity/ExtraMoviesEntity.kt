package com.example.moviebase.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(tableName = "extra_movies_table")
@TypeConverters(ExtraMovieTypeConverters::class)
data class ExtraMoviesEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val extraMovieType: ExtraMovieType
)

class ExtraMovieTypeConverters() {
    @TypeConverter
    fun fromIntegerToExtraMovieType(value: Int): ExtraMovieType {
        return when (value) {
            0 -> ExtraMovieType.RECOMMENDED
            1 -> ExtraMovieType.SIMILAR
            else -> ExtraMovieType.UNKNOWN
        }
    }

    @TypeConverter
    fun fromExtraMovieTypeToInteger(extraMovieType: ExtraMovieType): Int {
        return extraMovieType.code
    }
}


enum class ExtraMovieType(val code: Int) {
    RECOMMENDED(0),
    SIMILAR(1),
    UNKNOWN(-1)
}