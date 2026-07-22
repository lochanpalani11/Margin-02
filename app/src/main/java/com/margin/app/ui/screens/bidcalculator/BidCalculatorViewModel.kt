package com.margin.app.ui.screens.bidcalculator

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class BidCalculatorUiState(
    val bidAmount: String = ""
)

@HiltViewModel
class BidCalculatorViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(BidCalculatorUiState())
    val uiState: StateFlow<BidCalculatorUiState> = _uiState

    fun onBidAmountChange(value: String) {
        // Only allow valid decimal input
        if (value.isEmpty() || value.matches(Regex("^\\d*\\.?\\d{0,2}$"))) {
            _uiState.value = _uiState.value.copy(bidAmount = value)
        }
    }
}
