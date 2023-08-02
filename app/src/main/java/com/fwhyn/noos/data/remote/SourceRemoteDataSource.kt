package com.fwhyn.noos.data.remote

import com.fwhyn.noos.data.api.NewsClient
import com.fwhyn.noos.data.api.SourceInterface
import com.fwhyn.noos.data.helper.OnFailureListener
import com.fwhyn.noos.data.helper.OnSuccessListener
import com.fwhyn.noos.data.models.Source
import com.fwhyn.noos.data.models.SourceApiResponse
import com.fwhyn.noos.data.models.SourceRequestParameter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SourceRemoteDataSource @Inject constructor(client: NewsClient) {

    private val service = client.retrofit.create(SourceInterface::class.java)

    var onSuccessListener: OnSuccessListener<List<Source>>? = null
    var onFailureListener: OnFailureListener<Throwable>? = null

    fun getSources(parameter: SourceRequestParameter): SourceRemoteDataSource {
        val call = service.getSources(parameter.category)

        call.enqueue(object : Callback<SourceApiResponse> {

            override fun onResponse(call: Call<SourceApiResponse?>, response: Response<SourceApiResponse?>) {
                val sources = response.body()?.sources

                if (response.isSuccessful && sources != null) {
                    onSuccessListener?.onSuccess(sources)
                } else {
                    onFailureListener?.onFailure(Throwable(response.code().toString()))
                }
            }

            override fun onFailure(call: Call<SourceApiResponse?>, t: Throwable) {
                onFailureListener?.onFailure(t)
            }
        })

        return this
    }
}