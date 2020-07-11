package com.example.moviebase.view.widgets.mainview.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebase.R
import com.example.moviebase.model.representation.movies.MovieEntryModel

class MovieItemsRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    RecyclerView(context, attrs, defStyleAttr) {

    // region Constructors

    init {
        adapter = MoviesAdapter()
        layoutManager = object : LinearLayoutManager(context, RecyclerView.HORIZONTAL, false) {
            override fun supportsPredictiveItemAnimations(): Boolean {
                return true
            }
        }
        val mainMovieEntryItemDecoration =
            MovieItemItemDecoration(
                resources.getDimension(R.dimen.space_m).toInt(),
                resources.getDimension(R.dimen.space_m).toInt(),
                resources.getDimension(R.dimen.space_s).toInt(),
                resources.getDimension(R.dimen.space_m).toInt()
            )
        addItemDecoration(mainMovieEntryItemDecoration)
        overScrollMode = View.OVER_SCROLL_NEVER
    }

    // endregion

    // region Public Methods

    fun setMovies(movieEntries: List<MovieEntryModel>) {
        (adapter as MoviesAdapter).submitList(movieEntries)
    }

    // endregion
}