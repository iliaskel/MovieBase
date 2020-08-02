package com.example.moviebase.view.widgets.controlsrecycler.items

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bshg.homeconnect.app.ui2019.widgets.controlsrecycler.RecycleViewType
import com.bshg.homeconnect.app.ui2019.widgets.controlsrecycler.RecyclerViewItem
import com.example.moviebase.model.representation.MoviesRecyclerViewItemModel
import com.example.moviebase.view.widgets.mainview.MoviesView

class MoviesRecyclerViewItem(
    private val moviesRecyclerViewItemModel: MoviesRecyclerViewItemModel
) :
    RecyclerViewItem {

    companion object {
        val viewType = object : RecycleViewType {
            override fun getItemViewType(): Int = this.hashCode()

            override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
                return MovieViewHolder(
                    MoviesView(parent.context)
                )
            }
        }
    }

    override fun getItemViewType(): Int = viewType.getItemViewType()

    override fun bindView(holder: RecyclerView.ViewHolder, defaultId: Int) {
        super.bindView(holder, defaultId)
        if (holder is MovieViewHolder) {
            holder.moviesView.updateTitle(moviesRecyclerViewItemModel.title)
            holder.moviesView.setMovies(moviesRecyclerViewItemModel.moviesEntriesList)
        }
    }

    override fun isItemTheSame(itemToCompare: RecyclerViewItem): Boolean {
        return if (itemToCompare is MoviesRecyclerViewItem) {
            moviesRecyclerViewItemModel.title == itemToCompare.moviesRecyclerViewItemModel.title
        } else false
    }

    override fun isContentTheSame(itemToCompare: RecyclerViewItem): Boolean {
        return if (itemToCompare is MoviesRecyclerViewItem) {
            moviesRecyclerViewItemModel.moviesEntriesList.forEachIndexed { index, movie ->
                if (index >= itemToCompare.moviesRecyclerViewItemModel.moviesEntriesList.size) return false
                if (movie.id == itemToCompare.moviesRecyclerViewItemModel.moviesEntriesList[index].id &&
                    movie.posterPath == itemToCompare.moviesRecyclerViewItemModel.moviesEntriesList[index].posterPath &&
                    movie.movieType == itemToCompare.moviesRecyclerViewItemModel.moviesEntriesList[index].movieType &&
                    movie.title == itemToCompare.moviesRecyclerViewItemModel.moviesEntriesList[index].title &&
                    movie.releaseDate == itemToCompare.moviesRecyclerViewItemModel.moviesEntriesList[index].releaseDate &&
                    movie.voteAverage == itemToCompare.moviesRecyclerViewItemModel.moviesEntriesList[index].voteAverage &&
                    movie.voteCount == itemToCompare.moviesRecyclerViewItemModel.moviesEntriesList[index].voteCount
                ) return true
            }
            return false
        } else false
    }

    class MovieViewHolder(val moviesView: MoviesView) : RecyclerView.ViewHolder(moviesView)
}