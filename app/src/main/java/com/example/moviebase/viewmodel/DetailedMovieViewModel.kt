package com.example.moviebase.viewmodel

import androidx.lifecycle.LiveData
import com.bshg.homeconnect.app.ui2019.widgets.controlsrecycler.RecyclerViewItem
import com.example.moviebase.model.database.entity.DetailedMovieEntity
import com.example.moviebase.model.representation.movies.DetailedMovieModel

interface DetailedMovieViewModel {

    /**
     * Triggers the fetching of the details of a movie, given the movie's id.
     */
    fun fetchDetailedMovie(id: String)

    /**
     * Returns a [DetailedMovieEntity] as LiveData
     */
    fun getDetailedMovieModel(): LiveData<DetailedMovieModel>

    /**
     * Returns similar and recommended movies ([ExtraMovieType]) for the [DetailedMovieEntity] as [LiveData]
     */
    fun getExtraMovies(): LiveData<List<RecyclerViewItem>>
}