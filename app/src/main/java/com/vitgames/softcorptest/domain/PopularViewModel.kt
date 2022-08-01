package com.vitgames.softcorptest.domain

import androidx.lifecycle.ViewModel
import com.vitgames.softcorptest.data.RateDataInteractor
import javax.inject.Inject

class PopularViewModel @Inject constructor(private val interactor: RateDataInteractor) :
    ViewModel() {

    var currentData = interactor.getRateData()
}