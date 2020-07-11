package com.example.moviebase.view.widgets.mainview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.moviebase.R
import com.example.moviebase.model.representation.movies.MovieEntryModel
import kotlinx.android.synthetic.main.view_movies.view.*

class MoviesView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {

    // region Constructor

    init {
        LayoutInflater.from(context).inflate(R.layout.view_movies, this)
    }

    // endregion

    // region Public Methods

    fun updateTitle(title: String?) {
        if (!title.isNullOrEmpty()) {
            movies_view_title.visibility = View.VISIBLE
            movies_view_title.text = title
        } else {
            movies_view_title.visibility = View.GONE
            movies_view_title.text = title
        }
    }

    fun setMovies(movieEntries: List<MovieEntryModel>) {
        movies_view_movie_entries_recycler_view.setMovies(movieEntries)
    }

    // endregion

}