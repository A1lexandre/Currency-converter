package com.android.currencyconverter.data.model

data class ConvertionResponse(
    val date: String,
    val historical: String,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean
)