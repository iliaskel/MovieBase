package com.example.moviebase.model.network.playingnow

import com.google.gson.annotations.SerializedName

data class PlayingNowResponse(
    val page: Int?,
    val results: List<PlayingNowResult?>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)