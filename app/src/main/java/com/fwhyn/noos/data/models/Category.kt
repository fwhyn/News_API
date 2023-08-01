package com.fwhyn.noos.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Category(
    val id: String,
    val name: String,
    val colorCode: Int
) : Serializable