package com.fwhyn.noos.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fwhyn.noos.data.helper.OnSuccessListener
import com.fwhyn.noos.data.models.Category
import com.fwhyn.noos.data.repository.BaseDataRepository
import com.fwhyn.noos.ui.helper.CustomResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val categoryDataRepository: BaseDataRepository<Unit, List<Category>>
) : ViewModel() {

    private val _category = MutableLiveData<CustomResult<List<Category>>>()
    val category: LiveData<CustomResult<List<Category>>> = _category

    init {
        loadCategory()
    }

    private fun loadCategory() {
        categoryDataRepository
            .startGettingData(Unit)
            .addOnSuccessListener(object : OnSuccessListener<List<Category>> {
                override fun onSuccess(data: List<Category>) {
                    _category.value = CustomResult.Success(data)
                }
            })
    }
}