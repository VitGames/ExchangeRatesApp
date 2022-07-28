package com.vitgames.softcorptest.api

import com.google.gson.annotations.SerializedName
import com.vitgames.softcorptest.R

class RateModel {

    // TODO(апи возвращяет отличные от Нац Банка значения)

    @SerializedName("conversion_rates")
    private val rates: Rates? = null

    fun getRates(): Rates? = rates

    class Rates {

        @SerializedName("USD")
        private val usdRate: String? = null

        @SerializedName("UAH")
        private val uahRate: String? = null

        @SerializedName("RUB")
        private val rubRate: String? = null

        @SerializedName("PLN")
        private val plnRate: String? = null

        @SerializedName("BYN")
        private val bynRate: String? = null

        @SerializedName("EUR")
        private val eurRate: String? = null

        @SerializedName("GBP")
        private val gbpRate: String? = null

        @SerializedName("CNY")
        private val cnyRate: String? = null

        @SerializedName("KZT")
        private val kztRate: String? = null

        @SerializedName("CZK")
        private val czkRate: String? = null

        fun getRateData(): MutableList<RateData> {
            val data = mutableListOf<RateData>()
            data.add(RateData(R.drawable.ic_united_states, "USD", usdRate ?: ""))
            data.add(RateData(R.drawable.ic_european_union, "EUR", eurRate ?: ""))
            data.add(RateData(R.drawable.ic_belarus, "BYN", bynRate ?: ""))
            data.add(RateData(R.drawable.ic_russia, "RUB", rubRate ?: ""))
            data.add(RateData(R.drawable.ic_ukraine, "UAH", uahRate ?: ""))
            data.add(RateData(R.drawable.ic_poland, "PLN", plnRate ?: ""))
            data.add(RateData(R.drawable.ic_united_kingdom, "GBP", gbpRate ?: ""))
            data.add(RateData(R.drawable.ic_china, "CNY", cnyRate ?: ""))
            data.add(RateData(R.drawable.ic_kazakhstan, "KZT", kztRate ?: ""))
            data.add(RateData(R.drawable.ic_czech_republic, "CZK", czkRate ?: ""))
            return data
        }
    }
}

data class RateData(
    val icon: Int,
    val name: String,
    var value: String
)