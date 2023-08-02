package com.fwhyn.noos.data.repository

import com.fwhyn.noos.data.helper.OnFailureListener
import com.fwhyn.noos.data.helper.OnSuccessListener

interface BaseDataRepository<Input, Output> {
    fun startGettingData(input: Input): BaseDataRepository<Input, Output>

    fun addOnSuccessListener(listener: OnSuccessListener<Output>): BaseDataRepository<Input, Output> {
        return this
    }

    fun addOnFailureListener(listener: OnFailureListener<Throwable>): BaseDataRepository<Input, Output> {
        return this
    }
}