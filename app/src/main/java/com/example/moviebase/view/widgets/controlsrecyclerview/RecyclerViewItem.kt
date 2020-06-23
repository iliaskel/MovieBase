package com.bshg.homeconnect.app.ui2019.widgets.controlsrecycler

import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/** Interface for RecyclerView ViewItems  in Device Dashboard Recycler view
 *
 * every [RecyclerViewItem] should have its own [RecycleViewType] with the same ItemViewType to
 * be provided proper viewHolder */
interface RecyclerViewItem {

    /** RecyclerView viewType, should be same as in corresponding [RecycleViewType] */
    fun getItemViewType(): Int

    /** Method for binding data in RecyclerViewItem to its [RecyclerView.ViewHolder]
     *  an optional arg [defaultId] can be provided to set default recycler view id */
    @CallSuper
    fun bindView(holder: RecyclerView.ViewHolder, @IdRes defaultId: Int = -1) {
        if (defaultId != -1) {
            holder.itemView.id = defaultId
        }
    }

    /** [DiffUtil.ItemCallback] override for item comparison for [ListAdapter]
     * for establishing if current RecyclerItemView is same item as the other */
    fun isItemTheSame(itemToCompare: RecyclerViewItem): Boolean

    /** [DiffUtil.ItemCallback] override for item comparison for [ListAdapter]
     * for checking if item should be updated compared to it's previous state */
    fun isContentTheSame(itemToCompare: RecyclerViewItem): Boolean

}