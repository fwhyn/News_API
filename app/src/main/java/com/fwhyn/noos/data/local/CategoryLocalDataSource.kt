package com.fwhyn.noos.data.local

import com.fwhyn.noos.data.api.ArticleInterface
import com.fwhyn.noos.data.api.NewsClient
import com.fwhyn.noos.data.helper.OnFailureListener
import com.fwhyn.noos.data.helper.OnSuccessListener
import com.fwhyn.noos.data.helper.Utils
import com.fwhyn.noos.data.models.Article
import com.fwhyn.noos.data.models.ArticleApiResponse
import com.fwhyn.noos.data.models.ArticleRequestParameter
import com.fwhyn.noos.data.models.Category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CategoryLocalDataSource {

    val categories = List<Category>(
        Category()
    )


    fun getCategories: List<Category> {
        return categories
    }
}