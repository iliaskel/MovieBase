package com.example.moviebase.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.moviebase.R
import com.example.moviebase.view.widgets.controlsrecycler.ControlsRecyclerAdapter
import com.example.moviebase.view.widgets.controlsrecycler.itemdecorations.MainViewItemDecoration
import com.example.moviebase.view.widgets.controlsrecycler.items.MoviesRecyclerViewItem
import com.example.moviebase.viewmodel.MainViewModel
import com.example.moviebase.viewmodel.MainViewModelImpl
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ViewModelParameter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.androidx.viewmodel.koin.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

class MainFragment : Fragment() {

    // region Fields

    private val mainViewModel: MainViewModel by viewModel<MainViewModelImpl>()
    private val controlsRecyclerAdapter =
        ControlsRecyclerAdapter(
            MoviesRecyclerViewItem.viewType
        )

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
                MainViewItemDecoration(
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
        mainViewModel.getMoviesRecyclerItems().observe(viewLifecycleOwner, Observer {
            controlsRecyclerAdapter.submitList(it)
        })
    }
}