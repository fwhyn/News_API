package com.fwhyn.noos.data.remote

import com.fwhyn.noos.data.api.NewsClient
import com.fwhyn.noos.data.api.NewsInterface
import com.fwhyn.noos.data.helper.OnFailureListener
import com.fwhyn.noos.data.helper.OnSuccessListener
import com.fwhyn.noos.data.helper.Utils
import com.fwhyn.noos.data.models.Article
import com.fwhyn.noos.data.models.NewsApiResponse
import com.fwhyn.noos.data.models.NewsRequestParameter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(retrofitClient: NewsClient) {

    private val service = retrofitClient.retrofit.create(NewsInterface::class.java)
    private val country = Utils.country
    private val language = Utils.language

    var onSuccessListener: OnSuccessListener<List<Article>>? = null
    var onFailureListener: OnFailureListener<String>? = null

    fun getNews(newParameter: NewsRequestParameter): NewsRemoteDataSource {
        val keyword = newParameter.keyword

        val call: Call<NewsApiResponse> = if (!keyword.isNullOrEmpty()) {
            service.getNewsSearch(keyword, language, "publishedAt")
        } else {
            service.getNews(country)
        }

        call.enqueue(object : Callback<NewsApiResponse> {

            override fun onResponse(call: Call<NewsApiResponse?>, response: Response<NewsApiResponse?>) {
                val articles = response.body()?.article

                if (response.isSuccessful && articles != null) {
                    onSuccessListener?.onSuccess(articles)
                } else {
                    onFailureListener?.onFailure(response.code().toString())
                }
            }

            override fun onFailure(call: Call<NewsApiResponse?>, t: Throwable) {
                onFailureListener?.onFailure(t.toString())
            }
        })

        return this
    }
}