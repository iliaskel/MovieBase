package com.example.moviebase.view.widgets.detailedview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviebase.R
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.view_detailed_movie_poster_title.view.*

class DetailedMoviePosterTitleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) :
    ConstraintLayout(context, attrs) {

    // region Constructor

    init {
        LayoutInflater.from(context).inflate(R.layout.view_detailed_movie_poster_title, this)
    }

    // endregion

    // region Public Methods

    fun updateTitle(title: String?) {
        if (!title.isNullOrEmpty()) {
            detailed_movie_view_title.visibility = View.VISIBLE
            detailed_movie_view_title.text = title
        } else {
            detailed_movie_view_title.visibility = View.GONE
            detailed_movie_view_title.text = title
        }
    }

    fun setImages(posterPath: String) {
        Glide.with(this).load(posterPath).centerCrop().into(detailed_movie_poster_image)
        Glide.with(this).load(posterPath).apply(
            RequestOptions.bitmapTransform(BlurTransformation(25, 3))
        ).centerCrop().into(
            detailed_movie_poster_image_background
        )
    }

    // endregion

}