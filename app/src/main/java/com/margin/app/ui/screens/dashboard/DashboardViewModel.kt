package com.margin.app.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margin.app.domain.model.DashboardStats
import com.margin.app.domain.model.ProfitPoint
import com.margin.app.domain.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class DashboardUiState(
    val stats: DashboardStats = DashboardStats(),
    val profitOverTime: List<ProfitPoint> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    repository: ItemRepository
) : ViewModel() {

    private val refreshTrigger = MutableStateFlow(0L)

    val uiState: StateFlow<DashboardUiState> = combine(
        repository.observeDashboardStats(),
        repository.observeProfitOverTime(monthsBack = 6),
        refreshTrigger
    ) { stats, profitOverTime, _ ->
        DashboardUiState(stats = stats, profitOverTime = profitOverTime, isLoading = false)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardUiState()
    )

    fun refresh() {
        refreshTrigger.value++
    }
}
