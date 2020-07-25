package com.example.moviebase.view.widgets.controlsrecycler.items


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bshg.homeconnect.app.ui2019.widgets.controlsrecycler.RecycleViewType
import com.bshg.homeconnect.app.ui2019.widgets.controlsrecycler.RecyclerViewItem
import com.example.moviebase.view.widgets.detailedview.DetailedMovieDescriptionView
import kotlinx.android.synthetic.main.view_detailed_movie_description.view.*

class DetailedMovieDescriptionRecyclerViewItem(
    private val movieDescription: String
) :
    RecyclerViewItem {

    companion object {
        val viewType = object : RecycleViewType {
            override fun getItemViewType(): Int = this.hashCode()

            override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
                return DetailedMovieDescriptionViewHolder(
                    DetailedMovieDescriptionView(parent.context)
                )
            }
        }
    }

    override fun getItemViewType(): Int = viewType.getItemViewType()

    override fun bindView(holder: RecyclerView.ViewHolder, defaultId: Int) {
        super.bindView(holder, defaultId)
        if (holder is DetailedMovieDescriptionViewHolder) {
            holder.itemView.detailed_movie_description_text_view.text = movieDescription
        }
    }

    override fun isItemTheSame(itemToCompare: RecyclerViewItem): Boolean {
        return if (itemToCompare is DetailedMovieDescriptionRecyclerViewItem) {
            movieDescription == itemToCompare.movieDescription
        } else false
    }

    override fun isContentTheSame(itemToCompare: RecyclerViewItem): Boolean {
        return false
    }

    class DetailedMovieDescriptionViewHolder(detailedMovieViewDescriptionView: DetailedMovieDescriptionView) :
        RecyclerView.ViewHolder(detailedMovieViewDescriptionView)
}