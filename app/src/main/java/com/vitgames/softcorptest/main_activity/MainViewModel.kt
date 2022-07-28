package com.vitgames.softcorptest.main_activity

import androidx.lifecycle.ViewModel
import com.vitgames.softcorptest.RateDataInteractor
import javax.inject.Inject

class MainViewModel @Inject constructor(private val interactor: RateDataInteractor) : ViewModel() {

    private var currentAmount = 1
    private var currentRate = "USD"

    fun setAmount(amount: String) {
        val convertedAmount = amount.replace(".", "").toInt()
        // send request only if user set new amount
        if (currentAmount != convertedAmount) {
            currentAmount = convertedAmount
            sendRequest()
        }
    }

    fun setRateName(rateName: String) {
        // send request only if user set new rate
        if (currentRate != rateName) {
            currentRate = rateName
            sendRequest()
        }
    }

    fun sendRequest() {
        interactor.sendRequest(currentAmount, currentRate)
    }
}