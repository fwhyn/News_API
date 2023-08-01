package com.fwhyn.noos.data.models

import java.io.Serializable

data class ArticleRequestParameter(
    val keyword: String? = null,
    val category: String? = null,
    val source: String? = null
) : Serializable