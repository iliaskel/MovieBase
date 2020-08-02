package com.example.moviebase.view.widgets.mainview.recyclerview

import android.content.res.Resources
import android.graphics.Outline
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebase.R
import com.example.moviebase.model.database.entity.simplemovie.MovieType
import com.example.moviebase.model.representation.movies.MovieEntryModel
import com.example.moviebase.view.widgets.mainview.recyclerview.viewholder.MainMovieItemViewHolder
import com.example.moviebase.view.widgets.mainview.recyclerview.viewholder.PopularMoviesItemViewHolder
import kotlinx.android.synthetic.main.widget_fragment_main_movie_tile_list_item.view.*


const val MAIN_MOVIE_VIEW_TYPE = 0
const val POPULAR_MOVIE_VIEW_TYPE = 1

class MoviesAdapter :
    ListAdapter<MovieEntryModel, RecyclerView.ViewHolder>(
        MoviesDiffCallback()
    ) {

    // region Fields

    private var mainMovieCalculatedTileWidth: Int = 0
    private var mainMovieCalculateTileHeight: Int = 0
    private var popularMovieCalculateTileWidth: Int = 0
    private var popularMovieCalculateTileHeight: Int = 0

    // endregion

    // region Overrides

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == POPULAR_MOVIE_VIEW_TYPE) {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.widget_fragment_main_popular_movie_list_item, parent, false)
            return getCalculatedPopularMoviesViewHolder(itemView)
        }
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.widget_fragment_main_movie_tile_list_item, parent, false)
        return getCalculatedMainMoviesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = getItem(position)
        when (holder) {
            is MainMovieItemViewHolder -> holder.bindMovieViewHolder(movie)
            is PopularMoviesItemViewHolder -> holder.bindPopularMoviesViewHolder(movie)
        }


    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).movieType) {
            MovieType.POPULAR -> POPULAR_MOVIE_VIEW_TYPE
            else -> MAIN_MOVIE_VIEW_TYPE
        }
    }

    // endregion

    // region Private Methods

    private fun getCalculatedPopularMoviesViewHolder(itemView: View): PopularMoviesItemViewHolder {
        val viewHolder =
            PopularMoviesItemViewHolder(
                itemView
            )
        viewHolder.itemView.apply {
            if (popularMovieCalculateTileWidth == 0) {
                calculatePopularMovieTileSize(resources)
            }
            layoutParams.width = popularMovieCalculateTileWidth
            layoutParams.height = popularMovieCalculateTileHeight
        }

        return viewHolder
    }

    private fun calculatePopularMovieTileSize(resources: Resources) {
        if (popularMovieCalculateTileWidth != 0) {
            return
        }
        val density: Float = resources.displayMetrics.density
        val dpWidth: Float = resources.displayMetrics.widthPixels / density

        val recommendedWidth = dpWidth * 0.80

        popularMovieCalculateTileWidth = (recommendedWidth * density).toInt()
        popularMovieCalculateTileHeight =
            popularMovieCalculateTileWidth - (popularMovieCalculateTileWidth / 3)
    }

    private fun getCalculatedMainMoviesViewHolder(itemView: View): MainMovieItemViewHolder {
        val viewHolder =
            MainMovieItemViewHolder(
                itemView
            )
        viewHolder.itemView.apply {
            if (mainMovieCalculatedTileWidth == 0) {
                calculateMainMovieTileWidth(resources)
            }
            layoutParams.width = mainMovieCalculatedTileWidth
            layoutParams.height = mainMovieCalculateTileHeight
        }
        itemView.outlineProvider =
            CustomLayoutOutlineProvider(
                mainMovieCalculatedTileWidth,
                mainMovieCalculateTileHeight,
                itemView.movie_tile_cv.radius
            )
        return viewHolder
    }

    private fun calculateMainMovieTileWidth(resources: Resources) {
        if (mainMovieCalculatedTileWidth != 0) {
            return
        }
        val density: Float = resources.displayMetrics.density
        val dpWidth: Float = resources.displayMetrics.widthPixels / density

        val recommendedWidth = dpWidth * 0.30

        mainMovieCalculatedTileWidth = when {
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
        mainMovieCalculateTileHeight =
            (mainMovieCalculatedTileWidth) + (mainMovieCalculatedTileWidth / 3)
    }

    // endregion

}

class CustomLayoutOutlineProvider(
    private val width: Int,
    private val height: Int,
    private val radius: Float
) : ViewOutlineProvider() {

    override fun getOutline(view: View?, outline: Outline?) {
        outline?.setRoundRect(0, 0, width, height, radius);
    }
}