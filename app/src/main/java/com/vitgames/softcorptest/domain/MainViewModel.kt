package com.vitgames.softcorptest.domain

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vitgames.softcorptest.addToComposite
import com.vitgames.softcorptest.data.RateDataInteractor
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.Subscriptions
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel @Inject constructor(private val interactor: RateDataInteractor) : ViewModel(),
    NetworkConnectionListener {

    val userInputData = MutableLiveData<String>()
    val networkConnectionData = MutableLiveData<Boolean>()
    val progressBarData = interactor.getProgressData()

    private var currentAmount: Double = 1.0
    private var currentRate: String = "USD"
    private var isNetworkAvailable: Boolean = true

    private val initViewSubscription = Subscriptions.from()
    private val backgroundScheduler = Schedulers.io()
    private val mainThreadScheduler = AndroidSchedulers.mainThread()

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
            interactor.sendRequest(currentAmount, currentRate)
        } else {
            Log.e(javaClass.name, "Network connection is lost")
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
            .delay(1, TimeUnit.SECONDS)
            .subscribeOn(backgroundScheduler)
            .observeOn(mainThreadScheduler)
            .subscribe(
                { setAmount(it.toString()) },
                { Log.e(javaClass.name, "setInputAfterTextChangedListener error : " + it.localizedMessage) }
            ).addToComposite(initViewSubscription)
    }

    private fun filterUserInput(amountInput: String) {
        val firstChar = amountInput.first().toString()
        if (firstChar == ".") {
            userInputData.postValue(amountInput.substring(1))
        }
    }

    override fun onCleared() {
        initViewSubscription.clear()
        super.onCleared()
    }
}