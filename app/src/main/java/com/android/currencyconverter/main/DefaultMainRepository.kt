package com.android.currencyconverter.main

import com.android.currencyconverter.data.api.ApiService
import com.android.currencyconverter.data.model.ConversionResponse
import com.android.currencyconverter.util.Constants.Message.FAILURE_MESSAGE
import com.android.currencyconverter.util.Resource
import com.android.currencyconverter.util.Resource.Success
import com.android.currencyconverter.util.Resource.Error
import java.lang.Exception

class DefaultMainRepository constructor(
    private val apiService: ApiService,
) : MainRepository {

    override suspend fun convert(amount: String, from: String, to: String): Resource<ConversionResponse> {
        return try {
            val response = apiService.convert(amount, from, to)
            val result = response.body()
            if(response.isSuccessful && result != null)
                Success(result)
            else
                Error(response.message())
        } catch(e: Exception) {
            Error(e.message ?: FAILURE_MESSAGE)
        }
    }
}