package com.fwhyn.noos.data.models

import java.io.Serializable

data class NewsParameter(
    val keyword: String? = null,
    val category: String? = null
) : Serializable