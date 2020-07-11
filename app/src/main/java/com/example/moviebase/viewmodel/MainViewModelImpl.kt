package com.example.moviebase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bshg.homeconnect.app.ui2019.widgets.controlsrecycler.RecyclerViewItem
import com.example.moviebase.model.database.entity.MovieType
import com.example.moviebase.model.database.entity.MoviesEntity
import com.example.moviebase.model.repository.RepositoryImpl
import com.example.moviebase.model.representation.movies.MovieEntryModel
import com.example.moviebase.model.representation.MoviesRecyclerViewItemModel
import com.example.moviebase.view.widgets.controlsrecycler.items.MoviesRecyclerViewItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModelImpl(
    private val repository: RepositoryImpl
) : ViewModel(), MainViewModel {

    // region Implements

    override fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.replaceMovies()
        }
    }

    override fun getMovies(): LiveData<List<MovieEntryModel>> {
        return repository.getMovies().map { movieList ->
            movieList.toRepresentationModelList()
        }.asLiveData()
    }

    override fun getMoviesRecyclerItems(): LiveData<List<RecyclerViewItem>> {
        return repository.getMovies().map { movieList ->
            val items = mutableListOf<RecyclerViewItem>()
            movieList.toRepresentationModelList().apply {
                addMoviesByMovieType(MovieType.POPULAR, items)
                addMoviesByMovieType(MovieType.NOW_PLAYING, items)
                addMoviesByMovieType(MovieType.TOP_RATED, items)
                addMoviesByMovieType(MovieType.UPCOMING, items)
            }
            return@map items
        }.asLiveData()
    }

    private fun List<MovieEntryModel>.addMoviesByMovieType(
        movieType: MovieType,
        items: MutableList<RecyclerViewItem>
    ) {
        this.filter {
            it.movieType == movieType
        }.apply {
            items.add(
                MoviesRecyclerViewItem(
                    MoviesRecyclerViewItemModel(movieType.title, this)
                )
            )
        }
    }

    // endregion

    // region Extension Functions

    private fun MoviesEntity.toRepresentationModel(): MovieEntryModel {
        return MovieEntryModel(
            id = this.id,
            title = this.title,
            posterPath = this.posterPath,
            releaseDate = this.releaseDate,
            voteAverage = this.voteAverage,
            voteCount = this.voteCount,
            movieType = this.type,
            clickAction = getClickAction(this.id)
        )
    }

    private fun List<MoviesEntity>.toRepresentationModelList(): List<MovieEntryModel> {
        val movieList = mutableListOf<MovieEntryModel>()
        for (movie in this) {
            movieList.add(movie.toRepresentationModel())
        }
        return movieList
    }

    // endregion

    // region Private Functions

    private fun getClickAction(id: Int): (() -> Unit)? {
        // trigger navigation to detailed screen
        return null
    }

    // endregion
}