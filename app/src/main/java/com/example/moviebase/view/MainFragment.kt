package com.example.moviebase.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviebase.R
import com.example.moviebase.viewmodel.MainViewModelImpl
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
    }

    // endregion
}