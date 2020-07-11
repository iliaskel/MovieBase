package com.example.moviebase.view.widgets.mainview.recyclerview

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.moviebase.R
import com.example.moviebase.model.representation.MovieEntryModel

class MainMovieEntriesAdapter :
    ListAdapter<MovieEntryModel, MainMovieItemViewHolder>(
        MainMoviesDiffCallback()
    ) {

    // region Fields

    private var calculatedTileWidth: Int = 0
    private var calculatedTileTitleHeight: Int = 0

    // endregion

    // region Overrides

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMovieItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_movie_tile, parent, false)
        return getCalculatedViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainMovieItemViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bindMovieViewHolder(movie)
    }

    // endregion

    // region Private Methods

    private fun getCalculatedViewHolder(itemView: View): MainMovieItemViewHolder {
        val viewHolder = MainMovieItemViewHolder(itemView)
        viewHolder.itemView.apply {
            if (calculatedTileWidth == 0) {
                calculateTileWidth(resources)
            }
            layoutParams.width = calculatedTileWidth
            layoutParams.height = (calculatedTileWidth) + (calculatedTileWidth / 3)
        }
        return viewHolder
    }

    private fun calculateTileWidth(resources: Resources) {
        if (calculatedTileWidth != 0) {
            return
        }
        val density: Float = resources.displayMetrics.density
        val dpWidth: Float = resources.displayMetrics.widthPixels / density

        val recommendedWidth = dpWidth * 0.30

        calculatedTileWidth = when {
            recommendedWidth < 140 -> {
                (140 * density).toInt()
            }
            recommendedWidth > 280 -> {
                (280 * density).toInt()
            }
            else -> {
                (recommendedWidth * density).toInt()
            }
        }
    }

    // endregion

}