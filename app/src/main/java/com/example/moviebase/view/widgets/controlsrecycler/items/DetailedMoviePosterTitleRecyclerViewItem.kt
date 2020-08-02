package com.example.moviebase.view.widgets.controlsrecycler.items


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bshg.homeconnect.app.ui2019.widgets.controlsrecycler.RecycleViewType
import com.bshg.homeconnect.app.ui2019.widgets.controlsrecycler.RecyclerViewItem
import com.example.moviebase.view.widgets.detailedview.DetailedMoviePosterTitleView

class DetailedMoviePosterTitleRecyclerViewItem(
    private val movieTitle: String,
    private val moviePosterPath: String
) :
    RecyclerViewItem {

    companion object {
        val viewType = object : RecycleViewType {
            override fun getItemViewType(): Int = this.hashCode()

            override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
                return DetailedMoviePosterTitleViewHolder(
                    DetailedMoviePosterTitleView(parent.context)
                )
            }
        }
    }

    override fun getItemViewType(): Int = viewType.getItemViewType()

    override fun bindView(holder: RecyclerView.ViewHolder, defaultId: Int) {
        super.bindView(holder, defaultId)
        if (holder is DetailedMoviePosterTitleViewHolder) {
            holder.detailedMovieViewPosterTitleView.setTitle(movieTitle)
            holder.detailedMovieViewPosterTitleView.setImages(moviePosterPath)
        }
    }

    override fun isItemTheSame(itemToCompare: RecyclerViewItem): Boolean {
        return if (itemToCompare is DetailedMoviePosterTitleRecyclerViewItem) {
            movieTitle == itemToCompare.movieTitle
        } else false
    }

    override fun isContentTheSame(itemToCompare: RecyclerViewItem): Boolean {
        return if (itemToCompare is DetailedMoviePosterTitleRecyclerViewItem) {
            moviePosterPath == itemToCompare.moviePosterPath
        } else false
    }

    class DetailedMoviePosterTitleViewHolder(val detailedMovieViewPosterTitleView: DetailedMoviePosterTitleView) :
        RecyclerView.ViewHolder(detailedMovieViewPosterTitleView)
}