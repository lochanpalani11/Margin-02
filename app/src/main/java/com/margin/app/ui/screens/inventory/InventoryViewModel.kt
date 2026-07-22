package com.margin.app.ui.screens.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margin.app.domain.model.InventoryItem
import com.margin.app.domain.model.ItemStatus
import com.margin.app.domain.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SortOption(val label: String) {
    DATE("Date"),
    NAME("Name"),
    PROFIT("Profit")
}

data class InventoryUiState(
    val query: String = "",
    val statusFilter: ItemStatus? = null,
    val sortOption: SortOption = SortOption.DATE,
    val items: List<InventoryItem> = emptyList(),
    val isLoading: Boolean = true
)

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    private val query = MutableStateFlow("")
    private val statusFilter = MutableStateFlow<ItemStatus?>(null)
    private val sortOption = MutableStateFlow(SortOption.DATE)
    private val refreshTrigger = MutableStateFlow(0L)

    val uiState: StateFlow<InventoryUiState> = combine(query, statusFilter, sortOption, refreshTrigger) { q, f, s, _ ->
        Triple(q, f, s)
    }
        .flatMapLatest { (q, f, s) ->
            repository.observeItems(q, f).map { items ->
                val sorted = when (s) {
                    SortOption.DATE -> items.sortedByDescending { it.purchaseDate }
                    SortOption.NAME -> items.sortedBy { it.name.lowercase() }
                    SortOption.PROFIT -> items.sortedByDescending { it.profit ?: 0.0 }
                }
                InventoryUiState(
                    query = q,
                    statusFilter = f,
                    sortOption = s,
                    items = sorted,
                    isLoading = false
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = InventoryUiState()
        )

    fun onQueryChange(newQuery: String) {
        query.value = newQuery
    }

    fun onStatusFilterChange(status: ItemStatus?) {
        statusFilter.value = status
    }

    fun onSortChange(option: SortOption) {
        sortOption.value = option
    }

    fun refresh() {
        refreshTrigger.value++
    }

    fun markAsListed(id: Long) {
        viewModelScope.launch {
            repository.markAsListed(id)
        }
    }

    fun quickSell(itemId: Long, salePrice: Double, saleFees: Double) {
        viewModelScope.launch {
            repository.markAsSold(
                id = itemId,
                salePrice = salePrice,
                saleDate = System.currentTimeMillis(),
                salePlatform = "",
                saleFees = saleFees
            )
        }
    }
}
