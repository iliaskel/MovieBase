package com.example.moviebase.viewmodel

import androidx.lifecycle.LiveData
import com.bshg.homeconnect.app.ui2019.widgets.controlsrecycler.RecyclerViewItem
import com.example.moviebase.model.database.entity.simplemovie.MovieType
import com.example.moviebase.model.repository.MoviesRepository
import com.example.moviebase.model.representation.movies.MovieEntryModel

interface MainViewModel {

    /**
     * Triggers the fetching of movies of [MovieType] from the [MoviesRepository]
     */
    fun fetchMovies()

    /**
     * Returns movies of [MovieType] as LiveData
     */
    fun getMovies(): LiveData<List<MovieEntryModel>>

    /**
     * Returns the different types of movies (popular, top rated etc..) as a list of [RecyclerViewItem]
     */
    fun getMoviesRecyclerItems(): LiveData<List<RecyclerViewItem>>

}