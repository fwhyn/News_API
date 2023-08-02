package com.fwhyn.noos.data.repository

import com.fwhyn.noos.data.helper.OnFailureListener
import com.fwhyn.noos.data.helper.OnSuccessListener
import com.fwhyn.noos.data.models.Article
import com.fwhyn.noos.data.models.ArticleRequestParameter
import com.fwhyn.noos.data.remote.ArticleRemoteDataSource
import javax.inject.Inject

class ArticleDataRepository @Inject constructor(private val articleRemoteDataSource: ArticleRemoteDataSource) :
    BaseDataRepository<ArticleRequestParameter, List<Article>> {

    override fun startGettingData(input: ArticleRequestParameter): BaseDataRepository<ArticleRequestParameter, List<Article>> {
        articleRemoteDataSource.getArticles(input)

        return this
    }

    override fun addOnSuccessListener(listener: OnSuccessListener<List<Article>>):
            BaseDataRepository<ArticleRequestParameter, List<Article>> {
        articleRemoteDataSource.onSuccessListener = listener

        return this
    }

    override fun addOnFailureListener(listener: OnFailureListener<Throwable>):
            BaseDataRepository<ArticleRequestParameter, List<Article>> {
        articleRemoteDataSource.onFailureListener = listener

        return this
    }
}