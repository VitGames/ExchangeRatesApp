package com.vitgames.softcorptest.data.api

import com.vitgames.softcorptest.Constants.Companion.API_KEY
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class ApiManager {

    private val baseUrl = "https://v6.exchangerate-api.com/v6/$API_KEY/"
    private var retrofit: Retrofit? = null

    interface ApiInterface {
        @GET("https://v6.exchangerate-api.com/v6/$API_KEY/latest/{base}")
        fun getRates(@Path("base") base: String): Call<RateModel>
    }

    fun getClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}
