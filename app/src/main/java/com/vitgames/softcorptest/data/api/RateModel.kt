package com.vitgames.softcorptest.data.api

import com.google.gson.annotations.SerializedName

data class RateModel(
    @SerializedName("conversion_rates")
    val rates: Rate
)

data class Rate(
    @SerializedName("USD")
    val usdRate: String? = null,
    @SerializedName("UAH")
    val uahRate: String? = null,
    @SerializedName("RUB")
    val rubRate: String? = null,
    @SerializedName("PLN")
    val plnRate: String? = null,
    @SerializedName("BYN")
    val bynRate: String? = null,
    @SerializedName("EUR")
    val eurRate: String? = null,
    @SerializedName("GBP")
    val gbpRate: String? = null,
    @SerializedName("CNY")
    val cnyRate: String? = null,
    @SerializedName("KZT")
    val kztRate: String? = null,
    @SerializedName("CZK")
    val czkRate: String? = null,
)

data class RatePresentationModel(
    val id: Int,
    val icon: Int,
    val name: String,
    var value: String,
    val isSavedByUser: Boolean = false
)