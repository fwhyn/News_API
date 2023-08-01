package com.fwhyn.noos.data.repository

interface BaseDataRepository<Input, Output> {
    fun getData(input: Input): Output
}