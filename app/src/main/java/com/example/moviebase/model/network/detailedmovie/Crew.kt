package com.example.moviebase.model.network.detailedmovie

import com.google.gson.annotations.SerializedName

data class Crew(
    @SerializedName("credit_id")
    val creditId: String?,
    @SerializedName("profile_path")
    val profilePath: String?,
    val department: String?,
    val gender: Int?,
    val id: Int?,
    val job: String?,
    val name: String?
)