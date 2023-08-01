package com.fwhyn.noos.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SourceApiResponse(
    @SerializedName("status")
    @Expose
    var status: String? = null,

    @SerializedName("sources")
    @Expose
    var sources: List<Source>? = null
) : Serializable