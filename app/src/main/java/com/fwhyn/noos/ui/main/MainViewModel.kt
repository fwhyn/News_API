package com.fwhyn.noos.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fwhyn.noos.data.helper.OnFailureListener
import com.fwhyn.noos.data.helper.OnSuccessListener
import com.fwhyn.noos.data.models.Article
import com.fwhyn.noos.data.models.ArticleRequestParameter
import com.fwhyn.noos.data.repository.ArticleDataRepository
import com.fwhyn.noos.ui.helper.CustomResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val articleDataRepository: ArticleDataRepository) : ViewModel() {

    var temporaryKeyword: String = ""

    private val _articles = MutableLiveData<CustomResult<List<Article>>>()
    val articles: LiveData<CustomResult<List<Article>>> = _articles

    init {
        loadNews(temporaryKeyword)
    }

    fun loadNews(keyword: String) {
        _articles.value = CustomResult.Loading

        articleDataRepository.getNews(ArticleRequestParameter(keyword))
            .addOnSuccessListener(object : OnSuccessListener<List<Article>> {
                override fun onSuccess(data: List<Article>) {
                    _articles.value = CustomResult.Success(data)
                }

            })
            .addOnFailureListener(object : OnFailureListener<String> {
                override fun onFailure(error: String) {
                    _articles.value = CustomResult.Failure(error)
                }
            })
    }
}