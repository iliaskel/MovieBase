package com.example.moviebase.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bshg.homeconnect.app.ui2019.widgets.controlsrecycler.RecyclerViewItem
import com.example.moviebase.model.database.entity.ExtraMoviesEntity
import com.example.moviebase.model.repository.MoviesRepositoryImpl
import com.example.moviebase.model.representation.movies.ExtraMovieEntryModel
import com.example.moviebase.view.widgets.controlsrecycler.items.DetailedMovieDescriptionRecyclerViewItem
import com.example.moviebase.view.widgets.controlsrecycler.items.DetailedMoviePosterTitleRecyclerViewItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
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

    override fun getDetailedMovieRecyclerViewItems(): LiveData<List<RecyclerViewItem>> {
        return repository.getDetailedMovie().map {
            val posterTitleItem = DetailedMoviePosterTitleRecyclerViewItem(it.title, it.posterPath)
            val descriptionItem = DetailedMovieDescriptionRecyclerViewItem(it.overview)

            return@map listOf(posterTitleItem, descriptionItem)
        }.asLiveData()
    }

    override fun getExtraMovies(): LiveData<List<ExtraMovieEntryModel>> {
        return repository.getExtraMovies().map { extraMoviesList ->
            extraMoviesList.toRepresentationModelList()
        }.asLiveData()
    }

    // endregion

    // region Extension Functions

    private fun List<ExtraMoviesEntity>.toRepresentationModelList(): List<ExtraMovieEntryModel> {
        val extraMoviesList = mutableListOf<ExtraMovieEntryModel>()
        for (movie in this) {
            extraMoviesList.add(movie.toRepresentationModel())
        }
        return extraMoviesList
    }

    private fun ExtraMoviesEntity.toRepresentationModel(): ExtraMovieEntryModel {
        return ExtraMovieEntryModel(
            id = this.id,
            title = this.title,
            releaseDate = this.releaseDate,
            posterPath = this.posterPath,
            voteCount = this.voteCount,
            voteAverage = this.voteAverage,
            extraMovieType = this.extraMovieType,
            clickAction = getClickAction(this.id)
        )
    }

    // endregion

    // region Private Functions

    private fun getClickAction(id: Int): (view: View) -> Unit? {
        return {}
    }

    // endregion

}