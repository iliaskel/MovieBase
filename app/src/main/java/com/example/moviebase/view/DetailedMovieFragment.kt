package com.example.moviebase.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moviebase.R
import com.example.moviebase.view.widgets.controlsrecycler.ControlsRecyclerAdapter
import com.example.moviebase.view.widgets.controlsrecycler.ControlsRecyclerItemDecoration
import com.example.moviebase.viewmodel.DetailedMovieViewModelImpl
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ViewModelParameter
import org.koin.androidx.viewmodel.koin.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

class DetailedMovieFragment : Fragment() {


    // region Fields

    private val detailedMovieViewModel: DetailedMovieViewModelImpl by sharedGraphViewModel(R.id.nav_graph)
    private val controlsRecyclerAdapter = ControlsRecyclerAdapter()
    private val args: DetailedMovieFragmentArgs by navArgs()

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
        main_fragment_controls_recycler_view.apply {
            adapter = controlsRecyclerAdapter
            addItemDecoration(
                ControlsRecyclerItemDecoration(
                    resources.getDimension(R.dimen.space_s).toInt()
                )
            )
        }
        requestDetailedMovie()
//        initBindings()
    }

    // endregion

    // region Private methods

    private fun requestDetailedMovie() {
        detailedMovieViewModel.fetchDetailedMovie(args.movieId)
    }

//    private fun initBindings() {
//        detailedMovieViewModel.getDetailedMovie().observe(viewLifecycleOwner, Observer {
//            controlsRecyclerAdapter.submitList(it)
//        })
//    }

    // endregion

    private inline fun <reified VM : ViewModel> Fragment.sharedGraphViewModel(
        @IdRes navGraphId: Int,
        qualifier: Qualifier? = null,
        noinline parameters: ParametersDefinition? = null
    ) = lazy {
        val store = findNavController().getViewModelStoreOwner(navGraphId).viewModelStore
        getKoin().getViewModel(
            ViewModelParameter(
                VM::class,
                qualifier,
                parameters,
                null,
                store,
                null
            )
        )
    }
}