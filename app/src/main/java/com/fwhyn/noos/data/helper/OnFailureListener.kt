package com.fwhyn.noos.data.helper

interface OnFailureListener<ErrorResult> {
    fun onFailure(error: ErrorResult)
}