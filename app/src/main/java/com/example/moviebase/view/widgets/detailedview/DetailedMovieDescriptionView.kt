package com.example.moviebase.view.widgets.detailedview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.moviebase.R
import kotlinx.android.synthetic.main.view_detailed_movie_description.view.*

class DetailedMovieDescriptionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) :
    ConstraintLayout(context, attrs) {

    // region Constructor

    init {
        LayoutInflater.from(context).inflate(R.layout.view_detailed_movie_description, this)
        detailed_movie_description_label.text = "Description"
    }

    // endregion

    // region Public Methods

    fun setDescription(description: String?) {
        detailed_movie_description_text_view.text = description
    }

    // endregion

}