package com.android.currencyconverter.data.ui

import com.android.currencyconverter.data.model.ConversionResponse

sealed class ConversionEvent {
    class Success(val reponse: ConversionResponse): ConversionEvent()
    class Failure(val message: String): ConversionEvent()
    object Loading: ConversionEvent()
    object Empty: ConversionEvent()
}
