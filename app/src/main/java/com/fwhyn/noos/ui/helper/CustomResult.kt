package com.fwhyn.noos.ui.helper

sealed class CustomResult<out T> {

    data class Success<out T>(val value: T) : CustomResult<T>()

    object Loading : CustomResult<Nothing>()

    data class Failure(val throwable: Throwable) : CustomResult<Nothing>()
}