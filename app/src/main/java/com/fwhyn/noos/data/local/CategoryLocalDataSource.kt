package com.fwhyn.noos.data.local

import android.graphics.Color
import com.fwhyn.noos.data.models.Category

class CategoryLocalDataSource {

    private val categories = listOf(
        Category("business", "Business", Color.parseColor("#ffeead")),
        Category("entertainment", "Entertainment", Color.parseColor("#93cfb3")),
        Category("general", "General", Color.parseColor("#fd7a7a")),
        Category("health", "Health", Color.parseColor("#faca5f")),
        Category("science", "Science", Color.parseColor("#1ba798")),
        Category("sports", "Sports", Color.parseColor("#6aa9ae")),
        Category("technology", "Technology", Color.parseColor("#ffbf27"))
    )


    fun getCategories(): List<Category> {
        return categories
    }
}