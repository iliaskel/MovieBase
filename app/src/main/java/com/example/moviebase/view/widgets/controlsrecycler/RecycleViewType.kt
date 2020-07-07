package com.bshg.homeconnect.app.ui2019.widgets.controlsrecycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/** Interface handling creating [RecyclerView.ViewHolder] for specific [RecyclerViewItem]
 * every extension of [RecyclerViewItem] should define it's own [RecycleViewType] */
interface RecycleViewType {

    /** ItemViewType is used in viewHolder extension to determine which viewHolder should be created
     * for [RecyclerViewItem]
     *
     * [RecycleViewType]s are held in [ControlsRecyclerAdapter] in recyclerItemViews sparse array
     * with it's view type Int as a key and object as value */
    fun getItemViewType(): Int


    /** @return appropriate ViewHolder for the viewType basing on it's viewType Int */
    fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

}