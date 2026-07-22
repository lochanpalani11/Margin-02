package com.margin.app.ui.screens.itemdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margin.app.domain.model.InventoryItem
import com.margin.app.domain.repository.HaulRepository
import com.margin.app.domain.repository.ItemRepository
import com.margin.app.ui.util.ImageStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ItemDetailUiState(
    val item: InventoryItem? = null,
    val haulName: String? = null,
    val isLoading: Boolean = true,
    val isDeleted: Boolean = false
)

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val repository: ItemRepository,
    private val haulRepository: HaulRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val itemId: Long = checkNotNull(savedStateHandle.get<Long>("itemId"))

    private val isDeleted = MutableStateFlow(false)
    private val haulName = MutableStateFlow<String?>(null)

    val uiState: StateFlow<ItemDetailUiState> = kotlinx.coroutines.flow.combine(
        repository.observeItem(itemId),
        isDeleted,
        haulName
    ) { item, deleted, hName ->
        ItemDetailUiState(item = item, haulName = hName, isLoading = false, isDeleted = deleted)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ItemDetailUiState()
    )

    init {
        viewModelScope.launch {
            val item = repository.getItem(itemId)
            item?.haulId?.let { haulId ->
                val haul = haulRepository.getById(haulId)
                haulName.value = haul?.name
            }
        }
    }

    fun markAsListed() {
        viewModelScope.launch { repository.markAsListed(itemId) }
    }

    fun delete() {
        viewModelScope.launch {
            val item = repository.getItem(itemId)
            repository.deleteItem(itemId)
            ImageStorage.deleteImage(item?.imageUri)
            isDeleted.value = true
        }
    }
}
