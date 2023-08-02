package com.fwhyn.noos.ui.sources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fwhyn.noos.data.helper.OnFailureListener
import com.fwhyn.noos.data.helper.OnSuccessListener
import com.fwhyn.noos.data.models.Category
import com.fwhyn.noos.data.models.Source
import com.fwhyn.noos.data.models.SourceRequestParameter
import com.fwhyn.noos.data.repository.SourceDataRepository
import com.fwhyn.noos.ui.helper.CustomResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SourcesViewModel @Inject constructor(private val sourceDataRepository: SourceDataRepository) : ViewModel() {

    var temporaryKeyword: String = ""
    var category: Category? = null

    private val _sources = MutableLiveData<CustomResult<List<Source>>>()
    val sources: LiveData<CustomResult<List<Source>>> = _sources

    fun loadSources(category: Category?) {
        this.category = category
        _sources.value = CustomResult.Loading

        if (category != null) {
            sourceDataRepository.startGettingData(SourceRequestParameter(category.id))
                .addOnSuccessListener(object : OnSuccessListener<List<Source>> {
                    override fun onSuccess(data: List<Source>) {
                        _sources.value = CustomResult.Success(data)
                    }

                })
                .addOnFailureListener(object : OnFailureListener<Throwable> {
                    override fun onFailure(error: Throwable) {
                        _sources.value = CustomResult.Failure(error)
                    }
                })
        } else {
            _sources.value = CustomResult.Failure(Throwable("No Category Found"))
        }
    }
}