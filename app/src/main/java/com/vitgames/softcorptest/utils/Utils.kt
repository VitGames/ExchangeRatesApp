package com.vitgames.softcorptest.utils

import com.vitgames.softcorptest.R
import com.vitgames.softcorptest.data.api.Rate
import com.vitgames.softcorptest.data.api.RatePresentationModel
import com.vitgames.softcorptest.data.data_base.RateEntity
import rx.Subscription
import rx.subscriptions.CompositeSubscription

fun Subscription.addToComposite(compositeSubscription: CompositeSubscription) = compositeSubscription.add(this)

fun Rate.getRatePresentationModels(favoriteList: List<RateEntity>): MutableList<RatePresentationModel> {
    val defaultList = mutableListOf(
        RatePresentationModel(0, R.drawable.ic_united_states, "USD", usdRate ?: ""),
        RatePresentationModel(1, R.drawable.ic_european_union, "EUR", eurRate ?: ""),
        RatePresentationModel(2, R.drawable.ic_belarus, "BYN", bynRate ?: ""),
        RatePresentationModel(3, R.drawable.ic_russia, "RUB", rubRate ?: ""),
        RatePresentationModel(4, R.drawable.ic_ukraine, "UAH", uahRate ?: ""),
        RatePresentationModel(5, R.drawable.ic_poland, "PLN", plnRate ?: ""),
        RatePresentationModel(6, R.drawable.ic_united_kingdom, "GBP", gbpRate ?: ""),
        RatePresentationModel(7, R.drawable.ic_china, "CNY", cnyRate ?: ""),
        RatePresentationModel(8, R.drawable.ic_kazakhstan, "KZT", kztRate ?: ""),
        RatePresentationModel(9, R.drawable.ic_czech_republic, "CZK", czkRate ?: ""),
    )
    defaultList.forEach { defaultItem ->
        favoriteList.forEach { favoriteItem ->
            if (favoriteItem.rateId == defaultItem.id) {
                defaultItem.isFavorite = true
            }
        }
    }
    return defaultList
}