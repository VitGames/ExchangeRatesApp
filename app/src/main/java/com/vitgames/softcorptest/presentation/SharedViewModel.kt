package com.vitgames.softcorptest.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitgames.softcorptest.data.api.ApiManager
import com.vitgames.softcorptest.data.api.RateModel
import com.vitgames.softcorptest.data.api.RatePresentationModel
import com.vitgames.softcorptest.data.data_base.RateEntity
import com.vitgames.softcorptest.domain.LocalRateStorage
import com.vitgames.softcorptest.getRatePresentationModels
import com.vitgames.softcorptest.utils.NetworkConnectionListener
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SharedViewModel @Inject constructor(
    private val api: ApiManager.ApiInterface,
    private val storage: LocalRateStorage
) : ViewModel(), NetworkConnectionListener {

    val userInputData = MutableLiveData<String>()
    val networkConnectionData = MutableLiveData<Boolean>()
    val progressBarData = MutableLiveData<Boolean>()
    val currentData = MutableLiveData<List<RatePresentationModel>>()

    private var currentAmount: Double = 1.0
    private var currentRate: String = "USD"
    private var isNetworkAvailable: Boolean = true
    private var cachedRateData = emptyList<RatePresentationModel>()

    private val dataBaseErrorHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(javaClass.name, throwable.message ?: "Error during work with database")
    }
    private val coroutineContext = CoroutineScope(Dispatchers.IO + dataBaseErrorHandler).coroutineContext

    override fun onNetworkChange(isConnected: Boolean) {
        networkConnectionData.postValue(isConnected)
        isNetworkAvailable = isConnected.also { sendRequest() }
    }

    fun setRateName(rateName: String) {
        // send request only if user set new rate
        if (currentRate != rateName) {
            currentRate = rateName
            sendRequest()
        }
    }

    fun sendRequest() {
        if (isNetworkAvailable) {
            progressBarData.postValue(true)
            viewModelScope.launch(coroutineContext) {
                handleRequest(currentAmount, currentRate, storage.getAll())
            }
        } else {
            Log.e(javaClass.name, "Network connection is lost")
        }
    }

    fun onStarIconClick(item: RatePresentationModel) {
        if (item.isFavorite) {
            viewModelScope.launch(coroutineContext) {
                storage.delete(RateEntity(item.id))
            }
        } else {
            viewModelScope.launch(coroutineContext) {
                storage.insert(RateEntity(item.id))
            }
        }
        fetchData(item)
    }

    private fun fetchData(item: RatePresentationModel) {
        cachedRateData.map {
            if (it.id == item.id) {
                it.isFavorite = !it.isFavorite
            }
        }
        viewModelScope.launch {
            delay(660)
            currentData.postValue(cachedRateData)
        }
    }

    fun setInputAfterTextChangedListener(value: String) {
        if (value.isNotEmpty()) {
            filterUserInput(value)
            setAmount(value)
        }
    }

    fun getCachedRateData(): List<RatePresentationModel> = cachedRateData

    private fun handleRequest(amount: Double, rateName: String, favoriteList: List<RateEntity>) {
        val call: Call<RateModel> = api.getRates(rateName)

        call.enqueue(object : Callback<RateModel?> {
            override fun onResponse(call: Call<RateModel?>, response: Response<RateModel?>) {
                val newData = getFormattedRateList(
                    response.body()?.rates?.getRatePresentationModels(favoriteList),
                    amount,
                    rateName
                )
                cachedRateData = newData.also { currentData.postValue(it) }

                progressBarData.postValue(false)
            }

            override fun onFailure(call: Call<RateModel?>, t: Throwable) {
                //TODO
                Log.e(javaClass.name, t.localizedMessage)
                progressBarData.postValue(false)
            }
        })
    }

    private fun setAmount(amount: String) {
        // send request only if user set new amount
        val convertedAmount = amount.toDouble()
        if (currentAmount != convertedAmount && amount.isNotEmpty() && amount.first()
                .toString() != "."
        ) {
            currentAmount = convertedAmount
            sendRequest()
        }
    }

    private fun getFormattedRateList(
        data: MutableList<RatePresentationModel>?,
        amount: Double,
        rateName: String
    ): List<RatePresentationModel> {
        return if (data == null) {
            emptyList()
        } else {
            //  фильтруем список чтобы исключить появления ячейки с валютой,
            //  которую выбрал юзер
            val result = data.filter { it.name != rateName }
            if (amount != 1.0) {
                result.map {
                    val newRateValue: Double = it.value.toDouble() * amount
                    it.value = newRateValue.toString()
                }
                result
            } else result
        }
    }

    private fun filterUserInput(amountInput: String) {
        val firstChar = amountInput.first().toString()
        if (firstChar == ".") {
            amountInput.substring(1).also {
                userInputData.postValue(it)
            }
        }
    }
}