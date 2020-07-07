package com.example.moviebase.view.widgets.mainview.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviebase.model.representation.MovieEntryModel
import kotlinx.android.synthetic.main.list_item_movie_tile.view.*

class MainMovieItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindMovieViewHolder(movieEntryModel: MovieEntryModel) {
        Glide.with(itemView).load(movieEntryModel.posterPath).centerCrop()
            .into(itemView.movie_tile_movie_image)
    }


}