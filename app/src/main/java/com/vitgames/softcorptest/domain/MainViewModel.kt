package com.vitgames.softcorptest.domain

import android.util.Log
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitgames.softcorptest.data.RateDataInteractor
import rx.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel @Inject constructor(private val interactor: RateDataInteractor) : ViewModel() {

    val userInputData = MutableLiveData<String>()

    private var currentAmount: Double = 1.0
    private var currentRate: String = "USD"

    fun setRateName(rateName: String) {
        // send request only if user set new rate
        if (currentRate != rateName) {
            currentRate = rateName
            sendRequest()
        }
    }

    private fun setAmount(amount: String) {
        // send request only if user set new amount
        val convertedAmount = amount.toDouble()
        if (currentAmount != convertedAmount && amount.isNotEmpty()) {
            currentAmount = convertedAmount
            sendRequest()
        }
    }

    fun setInputAfterTextChangedListener(value: String) {
        Observable.just(value)
            .doOnSubscribe { filterUserInput(value) }
            .delay(2, TimeUnit.SECONDS)
            .subscribe(
                { setAmount(it.toString()) },
                {
                    Log.e(
                        "Error in ${javaClass.name}",
                        "setInputAfterTextChangedListener error" + it.localizedMessage
                    )
                }
            )
    }

    fun sendRequest() {
        interactor.sendRequest(currentAmount, currentRate)
    }

    private fun filterUserInput(amountInput: String) {
        val firstChar = amountInput.first().toString()
        if (firstChar == ".") {
            userInputData.postValue(amountInput.substring(1))
        }
    }
}