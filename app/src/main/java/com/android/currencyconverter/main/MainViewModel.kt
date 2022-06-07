package com.android.currencyconverter.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.currencyconverter.data.ui.ConversionEvent
import com.android.currencyconverter.util.Constants.Message.FAILURE_MESSAGE
import com.android.currencyconverter.util.DispatcherProvider
import com.android.currencyconverter.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatcherProvider: DispatcherProvider
): ViewModel() {
    private val _convertionState: MutableStateFlow<ConversionEvent> = MutableStateFlow(ConversionEvent.Empty)
    val convertionState = _convertionState

    fun convert(amount: String, from: String, to: String) {
        _convertionState.value = ConversionEvent.Loading
        viewModelScope.launch(dispatcherProvider.io) {
            delay(2000)
            when(val response = repository.convert(amount, from, to)) {
                is Resource.Success -> _convertionState.value = ConversionEvent.Success(response.data!!)
                else -> _convertionState.value = ConversionEvent.Failure(response.message ?: FAILURE_MESSAGE)
            }
        }
    }
}