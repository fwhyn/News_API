package com.fwhyn.noos.data.api

import com.fwhyn.noos.data.api.NewsClient.Companion.API_KEY
import com.fwhyn.noos.data.models.ArticleApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleInterface {

    @GET("top-headlines")
    fun getArticles(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<ArticleApiResponse>

    @GET("everything")
    fun getArticlesSearch(
        @Query("q") keyword: String,
        @Query("sources") sources: String,
        @Query("sortBy") sortBy: String = "relevancy",
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<ArticleApiResponse>
}