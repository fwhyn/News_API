package com.fwhyn.noos.data.repository

import com.fwhyn.noos.data.helper.OnFailureListener
import com.fwhyn.noos.data.helper.OnSuccessListener
import com.fwhyn.noos.data.models.Article
import com.fwhyn.noos.data.models.ArticleRequestParameter
import com.fwhyn.noos.data.remote.ArticleRemoteDataSource
import javax.inject.Inject

class ArticleDataRepository @Inject constructor(private val articleRemoteDataSource: ArticleRemoteDataSource) {
    fun getNews(articleRequestParameter: ArticleRequestParameter): ArticleDataRepository {
        articleRemoteDataSource.getNews(articleRequestParameter)

        return this
    }

    fun addOnSuccessListener(listener: OnSuccessListener<List<Article>>): ArticleDataRepository {
        articleRemoteDataSource.onSuccessListener = listener

        return this
    }

    fun addOnFailureListener(listener: OnFailureListener<String>): ArticleDataRepository {
        articleRemoteDataSource.onFailureListener = listener

        return this
    }
}