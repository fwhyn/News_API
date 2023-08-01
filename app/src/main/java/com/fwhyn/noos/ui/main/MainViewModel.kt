package com.fwhyn.noos.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fwhyn.noos.data.helper.OnFailureListener
import com.fwhyn.noos.data.helper.OnSuccessListener
import com.fwhyn.noos.data.models.Article
import com.fwhyn.noos.data.models.ArticleRequestParameter
import com.fwhyn.noos.data.models.Category
import com.fwhyn.noos.data.repository.CategoryDataRepository
import com.fwhyn.noos.ui.helper.CustomResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val categoryDataRepository: CategoryDataRepository) : ViewModel() {

    private val _category = MutableLiveData<CustomResult<List<Category>>>()
    val category: LiveData<CustomResult<List<Category>>> = _category

    init {
        loadCategory()
    }

    fun loadCategory() {
        _category.value = CustomResult.Success(categoryDataRepository.getData(Unit))
    }
}