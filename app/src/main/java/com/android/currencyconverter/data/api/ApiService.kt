package com.android.currencyconverter.data.api

import com.android.currencyconverter.data.model.ConversionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/exchangerates_data/convert?to={to}&from={from}&amount={amount}")
    suspend fun convert(@Query("amount") amount: String,
                        @Query("from") from: String,
                        @Query("to") to: String): Response<ConversionResponse>

}