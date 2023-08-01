package com.fwhyn.noos.data.api

import com.fwhyn.noos.data.api.NewsClient.Companion.API_KEY
import com.fwhyn.noos.data.models.ArticleApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SourceInterface {

    @GET("top-headlines/sources")
    fun getSources(
        @Query("category") category: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<ArticleApiResponse>
}