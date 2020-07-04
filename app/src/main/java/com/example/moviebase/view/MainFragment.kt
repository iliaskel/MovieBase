package com.example.moviebase.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.moviebase.R
import com.example.moviebase.viewmodel.MainViewModelImpl
import kotlinx.android.synthetic.main.view_main_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    // region Fields

    private val mainViewModel by viewModel<MainViewModelImpl>()

    // endregion

    // region Lifecycle overrides functions

    override

    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBindings()
    }

    // endregion

    // region Private methods

    private fun initBindings() {
        mainViewModel.getMovies().observe(viewLifecycleOwner, Observer {
            main_movies_view_title.text = "Movies"
            main_movies_view_movie_entries_recycler_view.setMovies(it)
        })
    }

    // endregion
}