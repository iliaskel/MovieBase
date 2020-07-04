package com.example.moviebase.view.widgets.mainview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.moviebase.R
import com.example.moviebase.model.representation.MovieEntryModel
import kotlinx.android.synthetic.main.view_main_movies.view.*

class MainMoviesView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {

    // region Constructor

    init {
        LayoutInflater.from(context).inflate(R.layout.view_main_movies, this)
    }

    // endregion

    // region Public Methods

    fun updateTitle(title: String?) {
        if (!title.isNullOrEmpty()) {
            main_movies_view_title.visibility = View.VISIBLE
            main_movies_view_title.text = title
        } else {
            main_movies_view_title.visibility = View.GONE
            main_movies_view_title.text = title
        }
    }

    fun setMovies(movieEntries: List<MovieEntryModel>) {
        main_movies_view_movie_entries_recycler_view.setMovies(movieEntries)
    }

    // endregion

}