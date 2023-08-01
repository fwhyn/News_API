package com.fwhyn.noos.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ArticleApiResponse(
    @SerializedName("status")
    @Expose
    var status: String? = null,

    @SerializedName("totalResult")
    @Expose
    var totalResult: Int = 0,

    @SerializedName("articles")
    @Expose
    var article: List<Article>? = null
) : Serializable