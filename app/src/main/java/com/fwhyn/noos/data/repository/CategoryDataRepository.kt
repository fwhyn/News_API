package com.fwhyn.noos.data.repository

import com.fwhyn.noos.data.helper.OnFailureListener
import com.fwhyn.noos.data.helper.OnSuccessListener
import com.fwhyn.noos.data.local.CategoryLocalDataSource
import com.fwhyn.noos.data.models.Article
import com.fwhyn.noos.data.models.Category
import javax.inject.Inject

class CategoryDataRepository @Inject constructor(private val categoryLocalDataSource: CategoryLocalDataSource) :
    BaseDataRepository<Unit, List<Category>> {

    override fun getData(input: Unit): List<Category> {
        return categoryLocalDataSource.getCategories()
    }
}