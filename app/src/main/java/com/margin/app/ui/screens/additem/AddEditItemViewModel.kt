package com.margin.app.ui.screens.additem

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margin.app.domain.model.Haul
import com.margin.app.domain.model.InventoryItem
import com.margin.app.domain.model.ItemStatus
import com.margin.app.domain.repository.HaulRepository
import com.margin.app.domain.repository.ItemRepository
import com.margin.app.ui.navigation.Destinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddEditItemUiState(
    val itemId: Long? = null,
    val name: String = "",
    val category: String = "",
    val purchasePrice: String = "",
    val purchaseDate: Long = System.currentTimeMillis(),
    val purchasePlatform: String = "",
    val notes: String = "",
    val imageUri: String? = null,
    val isEditing: Boolean = false,
    val isLoading: Boolean = true,
    val isSaved: Boolean = false,
    val haulName: String? = null,
    val selectedHaulId: Long? = null,
    val availableHauls: List<Haul> = emptyList(),
    val showHaulBanner: Boolean = false
) {
    val nameError: String?
        get() = if (name.isBlank()) "Name is required" else null

    val priceError: String?
        get() {
            val parsed = purchasePrice.toDoubleOrNull()
            return when {
                purchasePrice.isBlank() -> "Purchase price is required"
                parsed == null -> "Enter a valid number"
                parsed < 0 -> "Price can't be negative"
                else -> null
            }
        }

    val isValid: Boolean
        get() = nameError == null && priceError == null
}

@HiltViewModel
class AddEditItemViewModel @Inject constructor(
    private val repository: ItemRepository,
    private val haulRepository: HaulRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val itemId: Long = savedStateHandle.get<Long>("itemId") ?: Destinations.NO_ITEM_ID
    private val initialHaulId: Long? = savedStateHandle.get<Long>("haulId")?.takeIf { it != Destinations.NO_ITEM_ID }

    private val _state = MutableStateFlow(AddEditItemUiState(isLoading = itemId != Destinations.NO_ITEM_ID))

    val uiState: StateFlow<AddEditItemUiState> = combine(
        _state,
        haulRepository.observeAll()
    ) { state, hauls ->
        state.copy(availableHauls = hauls)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AddEditItemUiState()
    )

    val categorySuggestions: StateFlow<List<String>> = repository.observeCategories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        if (itemId != Destinations.NO_ITEM_ID) {
            viewModelScope.launch {
                val existing = repository.getItem(itemId)
                if (existing != null) {
                    _state.value = AddEditItemUiState(
                        itemId = existing.id,
                        name = existing.name,
                        category = existing.category,
                        purchasePrice = existing.purchasePrice.toString(),
                        purchaseDate = existing.purchaseDate,
                        purchasePlatform = existing.purchasePlatform,
                        notes = existing.notes,
                        imageUri = existing.imageUri,
                        isEditing = true,
                        isLoading = false,
                        selectedHaulId = existing.haulId
                    )
                } else {
                    _state.value = _state.value.copy(isLoading = false)
                }
            }
        } else if (initialHaulId != null) {
            // Pre-fill from haul context
            val haulId = initialHaulId
            viewModelScope.launch {
                val haul = haulRepository.getById(haulId)
                if (haul != null) {
                    _state.value = _state.value.copy(
                        purchaseDate = haul.date,
                        haulName = haul.name,
                        selectedHaulId = initialHaulId,
                        showHaulBanner = true,
                        isLoading = false
                    )
                } else {
                    _state.value = _state.value.copy(isLoading = false)
                }
            }
        } else {
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    fun onNameChange(value: String) { _state.value = _state.value.copy(name = value) }
    fun onCategoryChange(value: String) { _state.value = _state.value.copy(category = value) }
    fun onPriceChange(value: String) { _state.value = _state.value.copy(purchasePrice = value) }
    fun onDateChange(value: Long) { _state.value = _state.value.copy(purchaseDate = value) }
    fun onPlatformChange(value: String) { _state.value = _state.value.copy(purchasePlatform = value) }
    fun onNotesChange(value: String) { _state.value = _state.value.copy(notes = value) }
    fun onImagePicked(path: String?) { _state.value = _state.value.copy(imageUri = path) }

    fun onHaulSelected(haulId: Long?) {
        _state.value = _state.value.copy(selectedHaulId = haulId)
        val id = haulId
        if (id != null) {
            viewModelScope.launch {
                val haul = haulRepository.getById(id)
                if (haul != null) {
                    _state.value = _state.value.copy(
                        haulName = haul.name,
                        purchaseDate = haul.date
                    )
                }
            }
        } else {
            _state.value = _state.value.copy(haulName = null)
        }
    }

    fun save() {
        val state = _state.value
        if (!state.isValid) return

        viewModelScope.launch {
            val price = state.purchasePrice.toDouble()
            val effectiveHaulId = state.selectedHaulId ?: initialHaulId
            if (state.isEditing && state.itemId != null) {
                val existing = repository.getItem(state.itemId) ?: return@launch
                repository.updateItem(
                    existing.copy(
                        name = state.name.trim(),
                        category = state.category.trim().ifBlank { "Uncategorized" },
                        purchasePrice = price,
                        purchaseDate = state.purchaseDate,
                        purchasePlatform = state.purchasePlatform.trim(),
                        notes = state.notes.trim(),
                        imageUri = state.imageUri,
                        haulId = effectiveHaulId
                    )
                )
            } else {
                repository.addItem(
                    InventoryItem(
                        name = state.name.trim(),
                        category = state.category.trim().ifBlank { "Uncategorized" },
                        purchasePrice = price,
                        purchaseDate = state.purchaseDate,
                        purchasePlatform = state.purchasePlatform.trim(),
                        status = ItemStatus.IN_STOCK,
                        notes = state.notes.trim(),
                        imageUri = state.imageUri,
                        haulId = effectiveHaulId
                    )
                )
            }
            _state.value = _state.value.copy(isSaved = true)
        }
    }
}
