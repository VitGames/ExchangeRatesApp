package com.vitgames.softcorptest.popular

import androidx.lifecycle.ViewModel
import com.vitgames.softcorptest.RateDataInteractor
import javax.inject.Inject

class PopularViewModel @Inject constructor(private val interactor: RateDataInteractor) :
    ViewModel() {

    var currentData = interactor.getRateData()
}