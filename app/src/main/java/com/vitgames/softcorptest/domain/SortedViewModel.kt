package com.vitgames.softcorptest.domain

import androidx.lifecycle.ViewModel
import com.vitgames.softcorptest.RateDataInteractor
import javax.inject.Inject

class SortedViewModel @Inject constructor(
    private val interactor: RateDataInteractor
) : ViewModel()  {

}