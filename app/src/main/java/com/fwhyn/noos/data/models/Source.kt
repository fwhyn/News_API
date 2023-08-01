package com.fwhyn.noos.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Source(
    @SerializedName("id")
    @Expose
    var id: String? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("description")
    @Expose
    var description: String? = null,

    @SerializedName("url")
    @Expose
    var url: String? = null
) : Serializable