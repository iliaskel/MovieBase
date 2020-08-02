package com.example.moviebase.model.utils

import com.example.moviebase.utils.IMAGES_BASE_URL
import com.example.moviebase.utils.IMAGE_BIG_SIZE
import com.example.moviebase.utils.IMAGE_SMALL_SIZE

class ImagePathUtils {

    companion object {
        fun String.constructSmallPosterPath(): String {
            return IMAGES_BASE_URL.plus(IMAGE_SMALL_SIZE).plus(this)
        }

        fun String.constructBigPosterPath(): String {
            return IMAGES_BASE_URL.plus(IMAGE_BIG_SIZE).plus(this)
        }
    }

}