package com.example.moviebase.view.widgets.mainview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.moviebase.R
import com.example.moviebase.model.representation.movies.MovieEntryModel
import kotlinx.android.synthetic.main.view_movies.view.*
import kotlinx.android.synthetic.main.view_top_bar.view.*

class TopBarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {

    // region Constructor

    init {
        LayoutInflater.from(context).inflate(R.layout.view_top_bar, this)
        view_toolbar_movies_text_view.text = "Movies"
        view_toolbar_tv_shows_text_view.text = "Tv Shows"
        Glide.with(this).load(R.drawable.homescreengraphic_night).centerCrop()
            .into(view_toolbar_background_image)
    }

    // endregion

    // region Public Methods

    fun updateText(title: String?) {
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