package com.fwhyn.noos.data.remote

import com.fwhyn.noos.data.api.NewsClient
import com.fwhyn.noos.data.api.NewsInterface
import com.fwhyn.noos.data.helper.OnFailureListener
import com.fwhyn.noos.data.helper.OnSuccessListener
import com.fwhyn.noos.data.helper.Utils
import com.fwhyn.noos.data.models.Article
import com.fwhyn.noos.data.models.News
import com.fwhyn.noos.data.models.NewsParameter
import com.fwhyn.noos.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRemoteDataSource(
    retrofitClient: NewsClient,
) {

    private val service = retrofitClient.retrofit.create(NewsInterface::class.java)
    private val country = Utils.country
    private val language = Utils.language

    var onSuccessListener: OnSuccessListener<List<Article>>? = null
    var onFailureListener: OnFailureListener<String>? = null

    fun getNews(newParameter: NewsParameter): NewsRemoteDataSource {
        val keyword = newParameter.keyword

        val call: Call<News> = if (!keyword.isNullOrEmpty()) {
            service.getNewsSearch(keyword, language, "publishedAt", MainActivity.API_KEY)
        } else {
            service.getNews(country, MainActivity.API_KEY)
        }

        call.enqueue(object : Callback<News> {

            override fun onResponse(call: Call<News?>, response: Response<News?>) {
                val articles = response.body()?.article

                if (response.isSuccessful && articles != null) {
                    onSuccessListener?.onSuccess(articles)
                } else {
                    onFailureListener?.onFailure(response.code().toString())
                }
            }

            override fun onFailure(call: Call<News?>, t: Throwable) {
                onFailureListener?.onFailure(t.toString())
            }
        })

        return this
    }
}