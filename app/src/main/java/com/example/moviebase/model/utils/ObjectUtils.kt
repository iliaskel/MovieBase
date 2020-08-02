package com.example.moviebase.model.utils

class ObjectUtils {
    fun <T> hasNullField(vararg obj: T): Boolean {
        for (field in obj.javaClass.fields) {
            field ?: return true
        }
        return false
    }
}