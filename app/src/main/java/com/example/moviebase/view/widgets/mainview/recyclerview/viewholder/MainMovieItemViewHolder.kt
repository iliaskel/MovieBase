package com.example.moviebase.view.widgets.mainview.recyclerview.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviebase.model.representation.movies.MovieEntryModel
import kotlinx.android.synthetic.main.widget_fragment_main_movie_tile_list_item.view.*

class MainMovieItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindMovieViewHolder(movieEntryModel: MovieEntryModel) {
        Glide.with(itemView).load(movieEntryModel.posterPath).centerCrop()
            .into(itemView.popular_movie_tile_movie_image)
        itemView.setOnClickListener {
            movieEntryModel.clickAction.invoke(it)
        }
    }
}