package com.android.currencyconverter.data.ui

import com.android.currencyconverter.data.model.ConversionResponse

sealed class ConversionEvent {
    class Success(reponse: ConversionResponse): ConversionEvent()
    class Failure(message: String): ConversionEvent()
    object Loading: ConversionEvent()
    object Empty: ConversionEvent()
}
