package com.vitgames.softcorptest

import androidx.lifecycle.MutableLiveData
import com.vitgames.softcorptest.api.ApiManager
import com.vitgames.softcorptest.api.RateData
import com.vitgames.softcorptest.api.RateModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

interface RateDataInteractor {
    fun setBaseRate(base: String)
    fun getRateData(): MutableLiveData<MutableList<RateData>>
}

class RateDataInteractorImpl @Inject constructor() : RateDataInteractor {

    private val api = ApiManager().getClient()?.create(ApiManager.ApiInterface::class.java)
    private var currentData = MutableLiveData<MutableList<RateData>>()


    override fun setBaseRate(base: String) {
        handleResponse(base)
    }

    override fun getRateData(): MutableLiveData<MutableList<RateData>> = currentData

    private fun handleResponse(base: String){
        val callToday: Call<RateModel>? = api?.getRates(base)
        callToday?.enqueue(object : Callback<RateModel?> {

            override fun onResponse(call: Call<RateModel?>, response: Response<RateModel?>) {
                val data = response.body()?.getRates()?.getRateData()
                currentData.postValue(data)
            }

            override fun onFailure(call: Call<RateModel?>, t: Throwable) {
                // TODO(захэндлить ошибку запроса)
                t.localizedMessage
            }
        })
    }
}

