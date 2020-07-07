package com.example.moviebase.view.widgets.controlsrecycler

import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bshg.homeconnect.app.ui2019.widgets.controlsrecycler.RecycleViewType
import com.bshg.homeconnect.app.ui2019.widgets.controlsrecycler.RecyclerViewItem

class ControlsRecyclerAdapter(vararg viewTypes: RecycleViewType) :
    ListAdapter<RecyclerViewItem, RecyclerView.ViewHolder>(ControlsRecyclerDiffCallback()) {

    //region fields
    /** list of all possible view types for ViewHolders, created for fast access
     * [RecyclerViewItem] viewType should be equal to it's [RecycleViewType] viewType */
    private val recyclerItemViews = SparseArray<RecycleViewType>()
    //endregion

    //region constructor
    init {
        for (item in viewTypes) {
            recyclerItemViews.put(item.getItemViewType(), item)
        }
    }
    //endregion

    //region override
    override fun getItemViewType(position: Int): Int {
        return getItem(position).getItemViewType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        val item = recyclerItemViews.get(position) ?: throw Exception(
            "Uninitialized ViewType tried to create a ViewHolder. Check if ViewType is added to adapters constructor."
        )
        return item.createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position).bindView(holder)
    }
}