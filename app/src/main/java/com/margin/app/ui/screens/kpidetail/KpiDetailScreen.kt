package com.margin.app.ui.screens.kpidetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.margin.app.ui.components.EmptyState
import com.margin.app.ui.components.KpiCard
import com.margin.app.ui.components.ProfitBarChart
import com.margin.app.ui.components.ProfitIndicator
import com.margin.app.ui.components.ProfitLineChart
import com.margin.app.ui.components.swipeBack
import com.margin.app.ui.screens.analytics.AnalyticsRange
import com.margin.app.ui.theme.TextSecondary
import com.margin.app.ui.util.formatCurrency
import com.margin.app.ui.util.formatDays
import com.margin.app.ui.util.formatPercent

@Composable
fun KpiDetailScreen(
    kpiType: String,
    onBack: () -> Unit,
    viewModel: KpiDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val stats = uiState.stats
    var useLineChart by remember { mutableStateOf(true) }

    val title = when (kpiType) {
        "profit" -> "Profit Detail"
        "roi" -> "ROI Detail"
        "invested" -> "Invested Detail"
        "revenue" -> "Revenue Detail"
        "avgDays" -> "Average Days to Sell"
        else -> "Detail"
    }

    val subtitle = when (kpiType) {
        "profit" -> "Your net profit trend over time"
        "revenue" -> "Revenue from sold items"
        "invested" -> "Capital tied up in active inventory"
        "roi" -> "Return on your investments"
        "avgDays" -> "How long it takes to sell"
        else -> ""
    }

    val accentColor = MaterialTheme.colorScheme.primary
    val surfaceColor = MaterialTheme.colorScheme.surface

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize().swipeBack(onBack),
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // Hero gradient section — stock-market feel
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(24.dp))
                ) {
                    // Gradient background
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        accentColor.copy(alpha = 0.15f),
                                        surfaceColor,
                                        surfaceColor
                                    ),
                                    start = Offset(0f, 0f),
                                    end = Offset(0f, 500f)
                                )
                            )
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp)
                        ) {
                            // Title & subtitle in gradient area
                            Text(
                                text = title,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = subtitle,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary,
                                modifier = Modifier.padding(top = 4.dp)
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            // Chart header row with toggle
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Revenue Trend (${uiState.selectedRange.label})",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = TextSecondary
                                )
                                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                    IconToggleButton(
                                        checked = useLineChart,
                                        onCheckedChange = { useLineChart = true },
                                        modifier = Modifier.height(36.dp)
                                    ) {
                                        Icon(
                                            Icons.Filled.BarChart,
                                            contentDescription = "Line chart",
                                            tint = if (useLineChart) accentColor else TextSecondary,
                                            modifier = Modifier.padding(2.dp)
                                        )
                                    }
                                    IconToggleButton(
                                        checked = !useLineChart,
                                        onCheckedChange = { useLineChart = false },
                                        modifier = Modifier.height(36.dp)
                                    ) {
                                        Icon(
                                            Icons.Filled.BarChart,
                                            contentDescription = "Bar chart",
                                            tint = if (!useLineChart) accentColor else TextSecondary,
                                            modifier = Modifier.padding(2.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Chart
                            if (uiState.profitOverTime.isEmpty()) {
                                EmptyState(
                                    icon = Icons.Filled.BarChart,
                                    title = "No data to display",
                                    subtitle = "Your profit trend will appear here once you start selling items."
                                )
                            } else if (useLineChart) {
                                ProfitLineChart(
                                    points = uiState.profitOverTime,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            } else {
                                ProfitBarChart(
                                    points = uiState.profitOverTime,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Range filter chips inside the gradient card
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                AnalyticsRange.entries.forEach { range ->
                                    FilterChip(
                                        selected = uiState.selectedRange == range,
                                        onClick = { viewModel.onRangeSelected(range) },
                                        label = { Text(range.label) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = accentColor,
                                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Spacing between gradient card and KPI cards
            item { Spacer(modifier = Modifier.height(28.dp)) }

            // KPI summary cards
            when (kpiType) {
                "profit" -> {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        ) {
                            KpiCard(
                                label = "Total Profit",
                                modifier = Modifier.weight(1f)
                            ) {
                                ProfitIndicator(
                                    amount = stats.totalProfit,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                        }
                    }
                }
                "revenue" -> {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        ) {
                            KpiCard(label = "Total Revenue", modifier = Modifier.weight(1f)) {
                                Text(
                                    text = formatCurrency(stats.totalRevenue),
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                        }
                    }
                }
                "invested" -> {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        ) {
                            KpiCard(label = "Total Invested (Active)", modifier = Modifier.weight(1f)) {
                                Text(
                                    text = formatCurrency(stats.totalInvested),
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                        }
                    }
                }
                "roi" -> {
                    item {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        ) {
                            KpiCard(label = "Overall ROI", modifier = Modifier.weight(1f)) {
                                Text(
                                    text = formatPercent(stats.overallRoiPercent),
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                            KpiCard(label = "Total Profit", modifier = Modifier.weight(1f)) {
                                ProfitIndicator(
                                    amount = stats.totalProfit,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                        }
                    }
                }
                "avgDays" -> {
                    item {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        ) {
                            KpiCard(label = "Avg. Days to Sell", modifier = Modifier.weight(1f)) {
                                Text(
                                    text = formatDays(stats.averageDaysToSell.toLong()),
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                            KpiCard(label = "Items Sold", modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${stats.soldCount}",
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
