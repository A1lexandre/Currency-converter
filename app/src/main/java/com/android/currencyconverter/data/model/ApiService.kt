package com.android.currencyconverter.data.model

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/exchangerates_data/convert?to={to}&from={from}&amount={amount}")
    suspend fun convertCurrency(): Response<ConvertionResponse>

}