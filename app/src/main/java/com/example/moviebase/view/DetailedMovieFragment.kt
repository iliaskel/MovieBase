package com.example.moviebase.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.moviebase.R
import com.example.moviebase.view.widgets.controlsrecycler.ControlsRecyclerAdapter
import com.example.moviebase.view.widgets.controlsrecycler.itemdecorations.DetailedMovieViewItemDecoration
import com.example.moviebase.view.widgets.controlsrecycler.items.DetailedMovieDescriptionRecyclerViewItem
import com.example.moviebase.view.widgets.controlsrecycler.items.DetailedMoviePosterTitleRecyclerViewItem
import com.example.moviebase.viewmodel.DetailedMovieViewModel
import com.example.moviebase.viewmodel.DetailedMovieViewModelImpl
import kotlinx.android.synthetic.main.fragment_detailed_movie.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailedMovieFragment : Fragment() {


    // region Fields

    private val args: DetailedMovieFragmentArgs by navArgs()
    private val detailedMovieViewModel: DetailedMovieViewModel by viewModel<DetailedMovieViewModelImpl>()
    private val controlsRecyclerAdapter =
        ControlsRecyclerAdapter(
            DetailedMoviePosterTitleRecyclerViewItem.viewType,
            DetailedMovieDescriptionRecyclerViewItem.viewType
        )

    // endregion

    // region Lifecycle overrides functions

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detailed_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragment_detailed_movie_recycler_view.apply {
            adapter = controlsRecyclerAdapter
            addItemDecoration(
                DetailedMovieViewItemDecoration(
                    resources.getDimension(R.dimen.space_s).toInt(),
                    resources.getDimension(R.dimen.space_m).toInt(),
                    resources.getDimension(R.dimen.space_l).toInt()
                )
            )
        }
        requestDetailedMovie()
        initBindings()
    }

    // endregion

    // region Private methods

    private fun requestDetailedMovie() {
        detailedMovieViewModel.fetchDetailedMovie(args.movieId)
    }

    private fun initBindings() {
        detailedMovieViewModel.getDetailedMovieRecyclerViewItems()
            .observe(viewLifecycleOwner, Observer {
                controlsRecyclerAdapter.submitList(it)
            })
    }

    // endregion
}