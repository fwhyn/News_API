package com.fwhyn.noos.data.helper

interface OnFailureListener<Type> {
    fun onFailure(error: Type)
}