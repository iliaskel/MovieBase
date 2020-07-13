package com.example.moviebase.model.representation.movies

import android.view.View

interface IMovie {
    val id: Int
    val title: String
    val posterPath: String
    val releaseDate: String
    val voteAverage: Double
    val voteCount: Int
    val clickAction: (view: View) -> Unit?
}