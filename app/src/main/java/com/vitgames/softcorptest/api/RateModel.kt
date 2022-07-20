package com.vitgames.softcorptest.api

import com.google.gson.annotations.SerializedName

class RateModel {

    @SerializedName("base_code")
    private val base : String? = null

    @SerializedName("conversion_rates")
    private val rates : Rates? = null

    fun getRates(): Rates? = rates

    class Rates {

        @SerializedName("USD")
        private val usdRate : String? = null

        @SerializedName("AUD")
        private val audRate : String? = null

        @SerializedName("CAD")
        private val cadRate : String? = null

        @SerializedName("RUB")
        private val rubRate : String? = null

        @SerializedName("MXN")
        private val mxnRate : String? = null

        @SerializedName("PLN")
        private val plnRate : String? = null

        @SerializedName("BYN")
        private val bynRate : String? = null

        @SerializedName("EUR")
        private val eurRate : String? = null

        fun getRateData(): MutableList<RateData> {
            val data = mutableListOf<RateData>()
            data.add(RateData("USD",usdRate ?: ""))
            data.add(RateData("AUD",audRate ?: ""))
            data.add(RateData("CAD",cadRate ?: ""))
            data.add(RateData("RUB",rubRate ?: ""))
            data.add(RateData("PLN",plnRate ?: ""))
            data.add(RateData("BYN",bynRate ?: ""))
            data.add(RateData("EUR",eurRate ?: ""))
          return data
        }
    }
}

data class RateData(
     val name: String,
     val value: String
)