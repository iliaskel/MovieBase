package com.example.moviebase.viewmodel

import androidx.lifecycle.LiveData
import com.example.moviebase.model.database.entity.DetailedMovieEntity
import com.example.moviebase.model.database.entity.ExtraMovieType
import com.example.moviebase.model.representationmodel.ExtraMovie

interface DetailedMovieViewModel {

    /**
     * Triggers the fetching of the details of a movie, given the movie's id.
     */
    suspend fun fetchDetailedMovie(id: Int)

    /**
     * Returns a [DetailedMovieEntity] as LiveData
     */
    fun getDetailedMovie(): LiveData<DetailedMovieEntity>

    /**
     * Returns similar and recommended movies ([ExtraMovieType]) for the [DetailedMovieEntity] as [LiveData]
     */
    fun getExtraMovies(): LiveData<List<ExtraMovie>>
}