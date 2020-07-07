package com.example.moviebase.view.widgets.controlsrecycler

import androidx.recyclerview.widget.DiffUtil
import com.bshg.homeconnect.app.ui2019.widgets.controlsrecycler.RecyclerViewItem

/** Callback function using [RecyclerViewItem] inner comparison methods */
class ControlsRecyclerDiffCallback : DiffUtil.ItemCallback<RecyclerViewItem>() {

    override fun areItemsTheSame(
        oldItem: RecyclerViewItem,
        newItem: RecyclerViewItem
    ): Boolean {
        return oldItem.isItemTheSame(newItem)

    }

    override fun areContentsTheSame(
        oldItem: RecyclerViewItem,
        newItem: RecyclerViewItem
    ): Boolean {
        return oldItem.isContentTheSame(newItem)
    }

}