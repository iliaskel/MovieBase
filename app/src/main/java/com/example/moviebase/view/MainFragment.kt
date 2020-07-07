package com.example.moviebase.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviebase.R
import com.example.moviebase.view.widgets.controlsrecycler.ControlsRecyclerAdapter
import com.example.moviebase.view.widgets.controlsrecycler.ControlsRecyclerItemDecoration
import com.example.moviebase.view.widgets.controlsrecycler.items.MoviesRecyclerViewItem
import com.example.moviebase.viewmodel.MainViewModelImpl
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    // region Fields

    private val mainViewModel by viewModel<MainViewModelImpl>()
    private val controlsRecyclerAdapter =
        ControlsRecyclerAdapter(MoviesRecyclerViewItem.viewType)

    // endregion

    // region Lifecycle overrides functions

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        main_fragment_controls_recycler_view.apply {
            adapter = controlsRecyclerAdapter
            addItemDecoration(
                ControlsRecyclerItemDecoration(
                    resources.getDimension(R.dimen.space_s).toInt()
                )
            )
        }
        requestMovies()
        initBindings()
    }

    // endregion

    // region Private methods

    private fun requestMovies() {
        mainViewModel.fetchMovies()
    }

    private fun initBindings() {

    }

    // endregion
}