package com.fwhyn.noos.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fwhyn.noos.data.helper.OnFailureListener
import com.fwhyn.noos.data.helper.OnSuccessListener
import com.fwhyn.noos.data.models.Article
import com.fwhyn.noos.data.models.NewsParameter
import com.fwhyn.noos.data.repository.NewsDataRepository

class MainViewModel(private val newsDataRepository: NewsDataRepository) : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    fun loadNews(keyword: String) {

        newsDataRepository.getNews(NewsParameter(keyword))
            .addOnSuccessListener(object : OnSuccessListener<List<Article>> {
                override fun onSuccess(data: List<Article>) {
                    _articles.value = data
                }

            })
            .addOnFailureListener(object : OnFailureListener<String> {
                override fun onFailure(error: String) {

                }

            })
    }
}