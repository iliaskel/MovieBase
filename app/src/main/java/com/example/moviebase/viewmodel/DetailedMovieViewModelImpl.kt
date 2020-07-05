package com.example.moviebase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviebase.model.database.entity.DetailedMovieEntity
import com.example.moviebase.model.database.entity.ExtraMoviesEntity
import com.example.moviebase.model.repository.RepositoryImpl
import com.example.moviebase.model.representation.ExtraMovie
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DetailedMovieViewModelImpl(
    private val repository: RepositoryImpl
) : ViewModel(), DetailedMovieViewModel {

    // region Implements

    override suspend fun fetchDetailedMovie(id: Int) {
        viewModelScope.launch {
            repository.replaceDetailedMovie(id)
        }
    }

    override fun getDetailedMovie(): LiveData<DetailedMovieEntity> {
        return repository.getDetailedMovie().asLiveData()
    }

    override fun getExtraMovies(): LiveData<List<ExtraMovie>> {
        return repository.getExtraMovies().map { extraMoviesList ->
            extraMoviesList.toRepresentationModelList()
        }.asLiveData()
    }

    // endregion

    // region Extension Functions

    private fun List<ExtraMoviesEntity>.toRepresentationModelList(): List<ExtraMovie> {
        val extraMoviesList = mutableListOf<ExtraMovie>()
        for (movie in this) {
            extraMoviesList.add(movie.toRepresentationModel())
        }
        return extraMoviesList
    }

    private fun ExtraMoviesEntity.toRepresentationModel(): ExtraMovie {
        return ExtraMovie(
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

    private fun getClickAction(id: Int): (() -> Unit)? {
        // trigger navigation to detailed of the movie
        return null
    }

    // endregion

}