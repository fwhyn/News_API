package com.fwhyn.noos.data.local

import com.fwhyn.noos.data.models.Category

class CategoryLocalDataSource {

    private val categories = listOf(
        Category("business", "Business", 0),
        Category("entertainment", "Entertainment", 0),
        Category("general", "General", 0),
        Category("health", "Health", 0),
        Category("science", "Science", 0),
        Category("sports", "Sports", 0),
        Category("technology", "Technology", 0)
    )


    fun getCategories(): List<Category> {
        return categories
    }
}