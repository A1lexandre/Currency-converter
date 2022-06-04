package com.android.currencyconverter.util

import com.android.currencyconverter.BuildConfig.API_KEY

class Constants {

    object Api {
        const val BASE_URL = "https://api.apilayer.com"
        const val API_KEY_HEADER_NAME = "apikey"
        const val API_KEY_HEADER_VALUE = API_KEY
    }

    object Message {
        const val FAILURE_MESSAGE = "Something went wrong."
    }
}