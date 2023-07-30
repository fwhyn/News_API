package com.fwhyn.noos.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id")
    @Expose
    var id: String? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null
)