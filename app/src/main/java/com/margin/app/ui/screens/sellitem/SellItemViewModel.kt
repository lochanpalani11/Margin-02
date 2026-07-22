package com.margin.app.ui.screens.sellitem

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margin.app.domain.model.InventoryItem
import com.margin.app.domain.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SellItemUiState(
    val item: InventoryItem? = null,
    val salePrice: String = "",
    val saleFees: String = "0",
    val salePlatform: String = "",
    val saleDate: Long = System.currentTimeMillis(),
    val isLoading: Boolean = true,
    val isSaved: Boolean = false
) {
    val priceError: String?
        get() {
            val parsed = salePrice.toDoubleOrNull()
            return when {
                salePrice.isBlank() -> "Sale price is required"
                parsed == null -> "Enter a valid number"
                parsed < 0 -> "Price can't be negative"
                else -> null
            }
        }

    val feesError: String?
        get() {
            val parsed = saleFees.toDoubleOrNull()
            return when {
                saleFees.isBlank() -> null
                parsed == null -> "Enter a valid number"
                parsed < 0 -> "Fees can't be negative"
                else -> null
            }
        }

    val projectedProfit: Double?
        get() {
            val price = salePrice.toDoubleOrNull() ?: return null
            val fees = saleFees.toDoubleOrNull() ?: 0.0
            val cost = item?.purchasePrice ?: return null
            return price - cost - fees
        }

    val isValid: Boolean
        get() = priceError == null && feesError == null && item != null
}

@HiltViewModel
class SellItemViewModel @Inject constructor(
    private val repository: ItemRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val itemId: Long = checkNotNull(savedStateHandle.get<Long>("itemId"))

    private val _uiState = MutableStateFlow(SellItemUiState())
    val uiState: StateFlow<SellItemUiState> = _uiState

    init {
        viewModelScope.launch {
            val item = repository.getItem(itemId)
            _uiState.value = _uiState.value.copy(
                item = item,
                salePlatform = item?.purchasePlatform.orEmpty(),
                isLoading = false
            )
        }
    }

    fun onPriceChange(value: String) { _uiState.value = _uiState.value.copy(salePrice = value) }
    fun onFeesChange(value: String) { _uiState.value = _uiState.value.copy(saleFees = value) }
    fun onPlatformChange(value: String) { _uiState.value = _uiState.value.copy(salePlatform = value) }
    fun onDateChange(value: Long) { _uiState.value = _uiState.value.copy(saleDate = value) }

    fun confirmSale() {
        val state = _uiState.value
        if (!state.isValid) return
        viewModelScope.launch {
            repository.markAsSold(
                id = itemId,
                salePrice = state.salePrice.toDouble(),
                saleDate = state.saleDate,
                salePlatform = state.salePlatform.trim(),
                saleFees = state.saleFees.toDoubleOrNull() ?: 0.0
            )
            _uiState.value = _uiState.value.copy(isSaved = true)
        }
    }
}
