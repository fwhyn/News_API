package com.fwhyn.noos.data.api

import com.fwhyn.noos.data.api.NewsClient.Companion.API_KEY
import com.fwhyn.noos.data.models.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {

    @GET("top-headlines")
    fun getNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<News>

    @GET("everything")
    fun getNewsSearch(
        @Query("q") keyword: String,
        @Query("language") language: String,
        @Query("sortBy") sortBy: String = "relevancy",
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<News>
}