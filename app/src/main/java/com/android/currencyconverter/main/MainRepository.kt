package com.android.currencyconverter.main

import com.android.currencyconverter.data.model.ConversionResponse
import com.android.currencyconverter.util.Resource

interface MainRepository {
    suspend fun convert(amount: String, from: String, to: String): Resource<ConversionResponse>
}