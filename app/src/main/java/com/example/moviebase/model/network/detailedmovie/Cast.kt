package com.example.moviebase.model.network.detailedmovie

import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("cast_id")
    val castId: Int?,
    @SerializedName("credit_id")
    val creditId: String?,
    @SerializedName("profile_path")
    val profilePath: String?,
    val character: String?,
    val gender: Int?,
    val id: Int?,
    val name: String?,
    val order: Int?
)