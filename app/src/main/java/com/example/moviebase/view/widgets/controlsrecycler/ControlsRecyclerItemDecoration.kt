package com.example.moviebase.view.widgets.controlsrecycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebase.view.widgets.controlsrecycler.items.MoviesRecyclerViewItem

class ControlsRecyclerItemDecoration(private val outerItemOffset: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            val childAdapterPosition = parent.getChildAdapterPosition(view)
            if (childAdapterPosition != RecyclerView.NO_POSITION) {
                val itemCount = parent.adapter?.itemCount ?: 0
                val navigationViewType = MoviesRecyclerViewItem.viewType.getItemViewType()
                top = if (childAdapterPosition == 0) {
                    outerItemOffset
                } else 0
                bottom =
                    if (childAdapterPosition < itemCount - 1 && parent.adapter?.getItemViewType(
                            childAdapterPosition
                        ) == navigationViewType && parent.adapter?.getItemViewType(
                            childAdapterPosition + 1
                        ) != navigationViewType
                    ) {
                        outerItemOffset
                    } else 0
            }
        }
    }
}
