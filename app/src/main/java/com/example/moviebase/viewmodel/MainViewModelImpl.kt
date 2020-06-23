package com.example.moviebase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviebase.model.database.entity.MoviesEntity
import com.example.moviebase.model.repository.RepositoryImpl
import com.example.moviebase.model.representation.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModelImpl(
    private val repository: RepositoryImpl,
    application: Application
) : AndroidViewModel(application), MainViewModel {

    // region Implements

    override fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.replaceMovies()
        }
    }

    override fun getMovies(): LiveData<List<Movie>> {
        return repository.getMovies().map { movieList ->
            movieList.toRepresentationModelList()
        }.asLiveData()
    }

    // endregion

    // region Extension Functions

    private fun MoviesEntity.toRepresentationModel(): Movie {
        return Movie(
            id = this.id,
            title = this.title,
            posterPath = this.posterPath,
            releaseDate = this.releaseDate,
            voteAverage = this.voteAverage,
            voteCount = this.voteCount,
            type = this.type,
            clickAction = getClickAction(this.id)
        )
    }

    private fun List<MoviesEntity>.toRepresentationModelList(): List<Movie> {
        val movieList = mutableListOf<Movie>()
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