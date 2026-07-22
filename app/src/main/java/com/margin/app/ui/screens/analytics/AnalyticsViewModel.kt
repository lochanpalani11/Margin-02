package com.margin.app.ui.screens.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margin.app.domain.model.CategoryBreakdown
import com.margin.app.domain.model.DashboardStats
import com.margin.app.domain.model.ProfitPoint
import com.margin.app.domain.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

enum class AnalyticsRange(val label: String) {
    ONE_WEEK("1W"),
    ONE_MONTH("1M"),
    THREE_MONTHS("3M"),
    SIX_MONTHS("6M"),
    TWELVE_MONTHS("1Y")
}

data class AnalyticsUiState(
    val stats: DashboardStats = DashboardStats(),
    val profitOverTime: List<ProfitPoint> = emptyList(),
    val categoryBreakdown: List<CategoryBreakdown> = emptyList(),
    val selectedRange: AnalyticsRange = AnalyticsRange.SIX_MONTHS,
    val isLoading: Boolean = true
)

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    private val selectedRange = MutableStateFlow(AnalyticsRange.SIX_MONTHS)

    val uiState: StateFlow<AnalyticsUiState> = selectedRange
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
                profitFlow,
                repository.observeCategoryBreakdown()
            ) { stats, profitOverTime, categories ->
                AnalyticsUiState(
                    stats = stats,
                    profitOverTime = profitOverTime,
                    categoryBreakdown = categories,
                    selectedRange = range,
                    isLoading = false
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AnalyticsUiState()
        )

    fun onRangeSelected(range: AnalyticsRange) {
        selectedRange.value = range
    }
}
