package com.example.moviebase.view.widgets.mainview.recyclerview.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviebase.model.representation.movies.MovieEntryModel
import kotlinx.android.synthetic.main.widget_fragment_main_movie_tile_list_item.view.popular_movie_tile_movie_image
import kotlinx.android.synthetic.main.widget_fragment_main_popular_movie_list_item.view.*

class PopularMoviesItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    // region Public Methods

    fun bindPopularMoviesViewHolder(itemModel: MovieEntryModel) {
        itemView.popular_movie_tile_movie_image.apply {
            Glide.with(itemView).load(itemModel.posterPath).centerCrop()
                .into(itemView.popular_movie_tile_movie_image)
        }

        itemView.popular_movie_tile_movie_title.apply {
            this.text = itemModel.title
        }
    }

    // endregion

}
