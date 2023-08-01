package com.fwhyn.noos.data.remote

import com.fwhyn.noos.data.api.ArticleInterface
import com.fwhyn.noos.data.api.NewsClient
import com.fwhyn.noos.data.helper.OnFailureListener
import com.fwhyn.noos.data.helper.OnSuccessListener
import com.fwhyn.noos.data.helper.Utils
import com.fwhyn.noos.data.models.Article
import com.fwhyn.noos.data.models.ArticleApiResponse
import com.fwhyn.noos.data.models.ArticleRequestParameter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ArticleRemoteDataSource @Inject constructor(retrofitClient: NewsClient) {

    private val service = retrofitClient.retrofit.create(ArticleInterface::class.java)

    var onSuccessListener: OnSuccessListener<List<Article>>? = null
    var onFailureListener: OnFailureListener<String>? = null

    fun getArticles(newParameter: ArticleRequestParameter): ArticleRemoteDataSource {
        val keyword = newParameter.keyword

        val call: Call<ArticleApiResponse> = if (!keyword.isNullOrEmpty()) {
            service.getArticlesSearch(keyword, newParameter.source)
        } else {
            service.getArticles(newParameter.source)
        }

        call.enqueue(object : Callback<ArticleApiResponse> {

            override fun onResponse(call: Call<ArticleApiResponse?>, response: Response<ArticleApiResponse?>) {
                val articles = response.body()?.article

                if (response.isSuccessful && articles != null) {
                    onSuccessListener?.onSuccess(articles)
                } else {
                    onFailureListener?.onFailure(response.code().toString())
                }
            }

            override fun onFailure(call: Call<ArticleApiResponse?>, t: Throwable) {
                onFailureListener?.onFailure(t.toString())
            }
        })

        return this
    }
}