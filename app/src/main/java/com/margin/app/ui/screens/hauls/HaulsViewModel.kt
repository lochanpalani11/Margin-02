package com.margin.app.ui.screens.hauls

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margin.app.domain.model.Haul
import com.margin.app.domain.repository.HaulRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class HaulsUiState(
    val hauls: List<Haul> = emptyList(),
    val query: String = ""
)

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@HiltViewModel
class HaulsViewModel @Inject constructor(
    private val repository: HaulRepository
) : ViewModel() {

    private val query = MutableStateFlow("")
    private val refreshTrigger = MutableStateFlow(0L)

    val uiState: StateFlow<HaulsUiState> = combine(query, refreshTrigger) { q, _ -> q }
        .flatMapLatest { q ->
            repository.observeAll().map { hauls ->
                HaulsUiState(
                    hauls = if (q.isBlank()) hauls else hauls.filter { it.name.contains(q, ignoreCase = true) },
                    query = q
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HaulsUiState()
        )

    fun onQueryChange(newQuery: String) {
        query.value = newQuery
    }

    fun refresh() {
        refreshTrigger.value++
    }
}
