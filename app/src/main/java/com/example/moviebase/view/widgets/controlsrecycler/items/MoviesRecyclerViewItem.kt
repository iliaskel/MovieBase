package com.example.moviebase.view.widgets.controlsrecycler.items

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bshg.homeconnect.app.ui2019.widgets.controlsrecycler.RecycleViewType
import com.bshg.homeconnect.app.ui2019.widgets.controlsrecycler.RecyclerViewItem
import com.example.moviebase.model.representation.MoviesRecyclerViewItemModel
import com.example.moviebase.view.widgets.mainview.MainMoviesView

class MoviesRecyclerViewItem(
    private val moviesRecyclerViewItemModel: MoviesRecyclerViewItemModel
) :
    RecyclerViewItem {

    companion object {
        val viewType = object : RecycleViewType {
            override fun getItemViewType(): Int = this.hashCode()

            override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
                return MainMoviesViewHolder(
                    MainMoviesView(
                        parent.context
                    )
                )
            }
        }
    }

    override fun getItemViewType(): Int = viewType.getItemViewType()

    override fun bindView(holder: RecyclerView.ViewHolder, defaultId: Int) {
        super.bindView(holder, defaultId)
        if (holder is MainMoviesViewHolder) {
            holder.view.updateTitle(moviesRecyclerViewItemModel.title)
            holder.view.setMovies(moviesRecyclerViewItemModel.moviesEntriesList)
        }
    }

    override fun isItemTheSame(itemToCompare: RecyclerViewItem): Boolean {
        return if (itemToCompare is MoviesRecyclerViewItem) {
            moviesRecyclerViewItemModel.title == itemToCompare.moviesRecyclerViewItemModel.title
        } else false
    }

    override fun isContentTheSame(itemToCompare: RecyclerViewItem): Boolean {
        return if (itemToCompare is MoviesRecyclerViewItem) {
            moviesRecyclerViewItemModel == itemToCompare.moviesRecyclerViewItemModel
        } else false
    }

    class MainMoviesViewHolder(val view: MainMoviesView) : RecyclerView.ViewHolder(view)
}