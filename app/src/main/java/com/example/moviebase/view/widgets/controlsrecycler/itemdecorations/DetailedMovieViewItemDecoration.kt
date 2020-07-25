package com.example.moviebase.view.widgets.controlsrecycler.itemdecorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Custom ItemDecoration implementation for adding additional Start margin for first Tile
 */
class DetailedMovieViewItemDecoration(
    private val outerMargin: Int, private val marginTop: Int,
    private val marginBottom: Int
) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            val childAdapterPosition = parent.getChildAdapterPosition(view)
            val itemCount = parent.adapter?.itemCount ?: 0
            top = if (childAdapterPosition == 0) {
                marginTop
            } else {
                0
            }
            bottom = marginBottom
            left = outerMargin
            right = outerMargin
        }
    }
}