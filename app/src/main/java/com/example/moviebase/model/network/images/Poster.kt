package com.example.moviebase.model.network.images

import com.google.gson.annotations.SerializedName

data class Poster(
    @SerializedName("file_path")
    val filePath: String?,
    val height: Int?,
    val width: Int?
)