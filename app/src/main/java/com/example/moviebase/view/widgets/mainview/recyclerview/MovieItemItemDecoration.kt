package com.example.moviebase.view.widgets.mainview.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MovieItemItemDecoration(
    private val outerItemOffset: Int,
    private val spaceBetween: Int, private val marginTop: Int,
    private val marginBottom: Int
) :
    RecyclerView.ItemDecoration() {

    // region Override methods

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            val childAdapterPosition = parent.getChildAdapterPosition(view)
            val itemCount = parent.adapter?.itemCount ?: 0
            if (childAdapterPosition == 0) {
                left = outerItemOffset
            }
            top = marginTop
            bottom = marginBottom

            right = if (childAdapterPosition < itemCount - 1) {
                spaceBetween
            } else {
                outerItemOffset
            }
        }
    }

    // endregion
}