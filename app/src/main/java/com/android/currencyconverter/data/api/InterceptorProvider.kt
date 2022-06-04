package com.android.currencyconverter.data.api

import com.android.currencyconverter.util.Constants.Api.API_KEY_HEADER_NAME
import com.android.currencyconverter.util.Constants.Api.API_KEY_HEADER_VALUE
import okhttp3.OkHttpClient

object InterceptorProvider {
    fun getInterceptor(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.addHeader(API_KEY_HEADER_NAME, API_KEY_HEADER_VALUE)
                return@addInterceptor chain.proceed(builder.build())
            }
        }.build()
    }
}