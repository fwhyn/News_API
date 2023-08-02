package com.fwhyn.noos.data.repository

import com.fwhyn.noos.data.helper.OnFailureListener
import com.fwhyn.noos.data.helper.OnSuccessListener
import com.fwhyn.noos.data.local.CategoryLocalDataSource
import com.fwhyn.noos.data.models.Article
import com.fwhyn.noos.data.models.Category
import javax.inject.Inject

class CategoryDataRepository @Inject constructor(private val categoryLocalDataSource: CategoryLocalDataSource) :
    BaseDataRepository<Unit, List<Category>> {

    private var listener: OnSuccessListener<List<Category>>? = null

    override fun startGettingData(input: Unit): CategoryDataRepository {
        listener?.onSuccess(categoryLocalDataSource.getCategories())

        return this
    }

    override fun addOnSuccessListener(listener: OnSuccessListener<List<Category>>): CategoryDataRepository {
        this.listener = listener

        return this
    }
}