package com.example.moviebase.viewmodel

import androidx.lifecycle.LiveData
import com.example.moviebase.model.database.entity.MovieType
import com.example.moviebase.model.repository.Repository
import com.example.moviebase.model.representation.MovieEntryModel

interface MainViewModel {

    /**
     * Triggers the fetching of movies of [MovieType] from the [Repository]
     */
    fun fetchMovies()

    /**
     * Returns movies of [MovieType] as LiveData
     */
    fun getMovies(): LiveData<List<MovieEntryModel>>

}