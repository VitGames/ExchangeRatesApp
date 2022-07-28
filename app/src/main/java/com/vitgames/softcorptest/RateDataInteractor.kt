package com.vitgames.softcorptest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vitgames.softcorptest.api.ApiManager
import com.vitgames.softcorptest.api.RateData
import com.vitgames.softcorptest.api.RateModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

interface RateDataInteractor {
    fun sendRequest(amount: Int, rateName: String)
    fun getRateData(): LiveData<List<RateData>>
}

class RateDataInteractorImpl @Inject constructor() : RateDataInteractor {

    private val api = ApiManager().getClient()?.create(ApiManager.ApiInterface::class.java)
    private var currentData = MutableLiveData<List<RateData>>()

    override fun sendRequest(amount: Int, rateName: String) {
        handleResponse(amount, rateName)
    }

    override fun getRateData(): LiveData<List<RateData>> = currentData

    private fun handleResponse(amount: Int, rateName: String) {
        val callToday: Call<RateModel>? = api?.getRates(rateName)
        callToday?.enqueue(object : Callback<RateModel?> {

            override fun onResponse(call: Call<RateModel?>, response: Response<RateModel?>) {
                val responseData = response.body()?.getRates()?.getRateData()
                val formattedData = getFormattedRateList(responseData, amount, rateName)
                currentData.postValue(formattedData)
            }

            override fun onFailure(call: Call<RateModel?>, t: Throwable) {
                // TODO(захэндлить ошибку запроса)
                t.localizedMessage
            }
        })
    }

    private fun getFormattedRateList(
        data: MutableList<RateData>?,
        amount: Int,
        rateName: String
    ): List<RateData> {
        return if (data == null) {
            emptyList()
        } else {
            // filter out list to remove duplicate rate
            val result = data.filter { it.name != rateName }

            if (amount != 1) {
                result.map {
                    val newRateValue: Double = it.value.toDouble() * amount
                    it.value = newRateValue.toString()
                }
                result
            } else result
        }
    }
}

