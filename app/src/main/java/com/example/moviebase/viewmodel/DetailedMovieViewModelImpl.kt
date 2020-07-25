package com.example.moviebase.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bshg.homeconnect.app.ui2019.widgets.controlsrecycler.RecyclerViewItem
import com.example.moviebase.model.database.entity.DetailedMovieEntity
import com.example.moviebase.model.database.entity.MovieType
import com.example.moviebase.model.database.entity.MoviesEntity
import com.example.moviebase.model.repository.MoviesRepositoryImpl
import com.example.moviebase.model.representation.MoviesRecyclerViewItemModel
import com.example.moviebase.model.representation.movies.DetailedMovieModel
import com.example.moviebase.model.representation.movies.MovieEntryModel
import com.example.moviebase.view.widgets.controlsrecycler.items.MoviesRecyclerViewItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch

class DetailedMovieViewModelImpl(
    private val repository: MoviesRepositoryImpl
) : ViewModel(), DetailedMovieViewModel {

    // region Implements

    override fun fetchDetailedMovie(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.replaceDetailedMovie(id)
        }
    }

    override fun getDetailedMovieModel(): LiveData<DetailedMovieModel> {
        return repository.getDetailedMovie().mapNotNull {
            it.toRepresentationModel()
        }.asLiveData()
    }

    override fun getExtraMovies(): LiveData<List<RecyclerViewItem>> {
        return repository.getExtraMovies().map { extraMoviesList ->
            val items = mutableListOf<RecyclerViewItem>()
            extraMoviesList.toRepresentationModelList().apply {
                addMoviesByExtraMovieType(MovieType.EXTRA_MOVIES_RECOMMENDED, items)
                addMoviesByExtraMovieType(MovieType.EXTRA_MOVIES_SIMILAR, items)
            }
            return@map items
        }.asLiveData()
    }

    // endregion

    // region Extension Functions

    private fun List<MoviesEntity>.toRepresentationModelList(): List<MovieEntryModel> {
        return this.map {
            return@map MovieEntryModel(
                id = it.id,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                title = it.title,
                voteCount = it.voteCount,
                clickAction = getClickAction(),
                movieType = it.type
            )
        }
    }

    private fun List<MovieEntryModel>.addMoviesByExtraMovieType(
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

    private fun DetailedMovieEntity.toRepresentationModel(): DetailedMovieModel {
        return DetailedMovieModel(
            id = this.id,
            title = this.title,
            description = this.overview,
            releaseDate = this.releaseDate,
            posterPath = this.posterPath,
            voteCount = this.voteCount,
            voteAverage = this.voteAverage,
            clickAction = getClickAction(this.id)
        )
    }

    // endregion

    // region Private Functions

    private fun getClickAction(id: Int = 0): (view: View) -> Unit? {
        return {}
    }

    // endregion

}