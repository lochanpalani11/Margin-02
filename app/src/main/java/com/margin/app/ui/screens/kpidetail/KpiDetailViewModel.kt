package com.margin.app.ui.screens.kpidetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margin.app.domain.model.DashboardStats
import com.margin.app.domain.model.ProfitPoint
import com.margin.app.domain.repository.ItemRepository
import com.margin.app.ui.screens.analytics.AnalyticsRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class KpiDetailUiState(
    val stats: DashboardStats = DashboardStats(),
    val profitOverTime: List<ProfitPoint> = emptyList(),
    val selectedRange: AnalyticsRange = AnalyticsRange.SIX_MONTHS,
    val isLoading: Boolean = true
)

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@HiltViewModel
class KpiDetailViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    private val selectedRange = MutableStateFlow(AnalyticsRange.SIX_MONTHS)

    val uiState: StateFlow<KpiDetailUiState> = selectedRange
        .flatMapLatest { range ->
            val profitFlow = when (range) {
                AnalyticsRange.ONE_WEEK -> repository.observeProfitByDay(7)
                AnalyticsRange.ONE_MONTH -> repository.observeProfitByWeek(5)
                AnalyticsRange.THREE_MONTHS -> repository.observeProfitOverTime(3)
                AnalyticsRange.SIX_MONTHS -> repository.observeProfitOverTime(6)
                AnalyticsRange.TWELVE_MONTHS -> repository.observeProfitOverTime(12)
            }
            combine(
                repository.observeDashboardStats(),
                profitFlow
            ) { stats, profitOverTime ->
                KpiDetailUiState(
                    stats = stats,
                    profitOverTime = profitOverTime,
                    selectedRange = range,
                    isLoading = false
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = KpiDetailUiState()
        )

    fun onRangeSelected(range: AnalyticsRange) {
        selectedRange.value = range
    }
}
