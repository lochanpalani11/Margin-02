package com.margin.app.ui.screens.dashboard

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.margin.app.domain.model.DashboardStats
import com.margin.app.ui.components.AnimatedCountText
import com.margin.app.ui.components.AnimatedCurrencyText
import com.margin.app.ui.components.AnimatedPercentText
import com.margin.app.ui.components.AnimatedProfitIndicator
import com.margin.app.ui.components.EmptyState
import com.margin.app.ui.components.KpiCard
import com.margin.app.ui.components.MarginPullRefresh
import com.margin.app.ui.components.ProfitBarChart
import com.margin.app.ui.components.SectionHeader
import com.margin.app.ui.theme.FloatingAddButtonPadding
import com.margin.app.ui.theme.MarginRadius
import com.margin.app.ui.theme.SurfaceElevated1
import com.margin.app.ui.theme.TextSecondary

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onNavigateToInventory: (() -> Unit)? = null,
    onKpiDetail: ((String) -> Unit)? = null,
    onNavigateToBidCalculator: (() -> Unit)? = null,
    onNavigateToAddItem: (() -> Unit)? = null
) {
    val uiState by viewModel.uiState.collectAsState()
    val stats = uiState.stats

    MarginPullRefresh(
        onRefresh = { viewModel.refresh() },
        modifier = Modifier.fillMaxSize()
    ) { contentModifier ->
        LazyColumn(
            modifier = contentModifier.fillMaxSize(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                start = 20.dp,
                top = 24.dp,
                end = 20.dp,
                bottom = FloatingAddButtonPadding
            ),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            item {
                Column {
                    Text(text = "Margin", style = MaterialTheme.typography.displayLarge)
                    Text(
                        text = "Your flipping business, at a glance",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            item {
                KpiGrid(
                    stats = stats,
                    onNavigateToInventory = onNavigateToInventory,
                    onKpiDetail = onKpiDetail
                )
            }

            // Quick Add tile
            if (onNavigateToAddItem != null) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SurfaceElevated1, MarginRadius.Tile)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = onNavigateToAddItem
                            )
                            .padding(16.dp),
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Quick Add",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(end = 12.dp)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Quick Add Item",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Add a new flip to your inventory",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary
                            )
                        }
                        Text(text = "+", style = MaterialTheme.typography.titleLarge, color = TextSecondary)
                    }
                }
            }

            if (onNavigateToBidCalculator != null) {
                item {
                    val interactionSource = remember { MutableInteractionSource() }
                    val isPressed by interactionSource.collectIsPressedAsState()
                    val haptic = LocalHapticFeedback.current
                    val scale by animateFloatAsState(
                        targetValue = if (isPressed) 0.96f else 1f,
                        animationSpec = spring(dampingRatio = 0.5f, stiffness = 400f),
                        label = "bidCalcScale"
                    )

                    LaunchedEffect(isPressed) {
                        if (isPressed) haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .scale(scale)
                            .background(SurfaceElevated1, MarginRadius.Tile)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                onClick = onNavigateToBidCalculator
                            )
                            .padding(20.dp),
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Calculate,
                            contentDescription = "Bid Calculator",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "MacBid Calculator",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Quickly calculate total cost with fees & tax",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary
                            )
                        }
                        Text(
                            text = "\u2192",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextSecondary
                        )
                    }
                }
            }

            item {
                Column {
                    SectionHeader(title = "Profit, last 6 months")
                    Spacer(modifier = Modifier.height(12.dp))
                    if (uiState.profitOverTime.isEmpty()) {
                        EmptyState(
                            icon = Icons.Filled.BarChart,
                            title = "No data to display",
                            subtitle = "Your profit trend will appear here once you start selling items."
                        )
                    } else {
                        ProfitBarChart(
                            points = uiState.profitOverTime,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun KpiGrid(
    stats: DashboardStats,
    onNavigateToInventory: (() -> Unit)?,
    onKpiDetail: ((String) -> Unit)?
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        KpiCard(
            label = "Total Profit",
            modifier = Modifier.fillMaxWidth(),
            onClick = if (onKpiDetail != null) {{ onKpiDetail("profit") }} else null
        ) {
            AnimatedProfitIndicator(amount = stats.totalProfit, style = MaterialTheme.typography.headlineMedium)
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            KpiCard(
                label = "Revenue (sold)",
                modifier = Modifier.weight(1f),
                onClick = if (onKpiDetail != null) {{ onKpiDetail("revenue") }} else null
            ) {
                AnimatedCurrencyText(amount = stats.totalRevenue, style = MaterialTheme.typography.headlineMedium)
            }
            KpiCard(
                label = "Invested (active)",
                modifier = Modifier.weight(1f),
                onClick = if (onKpiDetail != null) {{ onKpiDetail("invested") }} else null
            ) {
                AnimatedCurrencyText(amount = stats.totalInvested, style = MaterialTheme.typography.headlineMedium)
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            KpiCard(
                label = "Return on Investment",
                modifier = Modifier.weight(1f),
                onClick = if (onKpiDetail != null) {{ onKpiDetail("roi") }} else null
            ) {
                AnimatedPercentText(percent = stats.overallRoiPercent, style = MaterialTheme.typography.headlineMedium)
            }
            KpiCard(
                label = "In Stock / Listed",
                modifier = Modifier.weight(1f),
                onClick = onNavigateToInventory
            ) {
                Row {
                    AnimatedCountText(count = stats.inStockCount, style = MaterialTheme.typography.headlineMedium)
                    Text(" / ", style = MaterialTheme.typography.headlineMedium)
                    AnimatedCountText(count = stats.listedCount, style = MaterialTheme.typography.headlineMedium)
                }
            }
        }
    }
}
