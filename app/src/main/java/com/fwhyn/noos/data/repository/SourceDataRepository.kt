package com.fwhyn.noos.data.repository

import com.fwhyn.noos.data.helper.OnFailureListener
import com.fwhyn.noos.data.helper.OnSuccessListener
import com.fwhyn.noos.data.models.Source
import com.fwhyn.noos.data.models.SourceRequestParameter
import com.fwhyn.noos.data.remote.SourceRemoteDataSource
import javax.inject.Inject

class SourceDataRepository @Inject constructor(private val sourceRemoteDataSource: SourceRemoteDataSource) :
    BaseDataRepository<SourceRequestParameter, SourceDataRepository> {

    override fun getData(input: SourceRequestParameter): SourceDataRepository {
        sourceRemoteDataSource.getSources(input)

        return this
    }

    fun addOnSuccessListener(listener: OnSuccessListener<List<Source>>): SourceDataRepository {
        sourceRemoteDataSource.onSuccessListener = listener

        return this
    }

    fun addOnFailureListener(listener: OnFailureListener<String>): SourceDataRepository {
        sourceRemoteDataSource.onFailureListener = listener

        return this
    }
}