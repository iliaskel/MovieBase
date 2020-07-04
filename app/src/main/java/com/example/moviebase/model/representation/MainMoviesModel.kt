package com.example.moviebase.model.representation

import kotlin.random.Random

data class MainMoviesModel(
    val id: String = Random.nextInt().toString(),
    val title: String = "Movies",
    val movieEntryModels: List<MovieEntryModel>
)