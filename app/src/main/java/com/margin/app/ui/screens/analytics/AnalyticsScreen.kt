package com.margin.app.ui.screens.analytics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.margin.app.ui.components.CategoryBreakdownList
import com.margin.app.ui.components.EmptyState
import com.margin.app.ui.components.KpiCard
import com.margin.app.ui.components.ProfitBarChart
import com.margin.app.ui.components.SectionHeader
import com.margin.app.ui.util.formatDays
import com.margin.app.ui.util.formatPercent

@Composable
fun AnalyticsScreen(
    viewModel: AnalyticsViewModel = hiltViewModel(),
    onKpiDetail: ((String) -> Unit)? = null
) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 20.dp,
            vertical = 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        item {
            Text(text = "Analytics", style = MaterialTheme.typography.displayLarge)
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AnalyticsRange.entries.forEach { range ->
                    FilterChip(
                        selected = uiState.selectedRange == range,
                        onClick = { viewModel.onRangeSelected(range) },
                        label = { Text(range.label) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
        }

        item {
            Column {
                SectionHeader(title = "Profit trend")
                Spacer(modifier = Modifier.height(12.dp))
                if (uiState.profitOverTime.isEmpty()) {
                    EmptyState(
                        icon = Icons.Filled.BarChart,
                        title = "No data to display",
                        subtitle = "Your profit trend will appear here once you start selling items."
                    )
                } else {
                    ProfitBarChart(points = uiState.profitOverTime)
                }
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                KpiCard(
                    label = "Overall ROI",
                    modifier = Modifier.weight(1f),
                    onClick = if (onKpiDetail != null) {{ onKpiDetail("roi") }} else null
                ) {
                    Text(
                        text = formatPercent(uiState.stats.overallRoiPercent),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                KpiCard(
                    label = "Avg. Days to Sell",
                    modifier = Modifier.weight(1f),
                    onClick = if (onKpiDetail != null) {{ onKpiDetail("avgDays") }} else null
                ) {
                    Text(
                        text = formatDays(uiState.stats.averageDaysToSell.toLong()),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }

        item {
            Column {
                SectionHeader(title = "Profit by category")
                Spacer(modifier = Modifier.height(12.dp))
                CategoryBreakdownList(categories = uiState.categoryBreakdown)
            }
        }
    }
}
