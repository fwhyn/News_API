package com.fwhyn.noos.data.repository

import com.fwhyn.noos.data.helper.OnFailureListener
import com.fwhyn.noos.data.helper.OnSuccessListener
import com.fwhyn.noos.data.models.Article
import com.fwhyn.noos.data.models.NewsParameter
import com.fwhyn.noos.data.remote.NewsRemoteDataSource

class NewsDataRepository(
    private val newsRemoteDataSource: NewsRemoteDataSource
) {
    fun getNews(newsParameter: NewsParameter): NewsDataRepository {
        newsRemoteDataSource.getNews(newsParameter)

        return this
    }

    fun addOnSuccessListener(listener: OnSuccessListener<List<Article>>): NewsDataRepository {
        newsRemoteDataSource.onSuccessListener = listener

        return this
    }

    fun addOnFailureListener(listener: OnFailureListener<String>): NewsDataRepository {
        newsRemoteDataSource.onFailureListener = listener

        return this
    }
}