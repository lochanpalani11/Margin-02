package com.margin.app.ui.screens.more

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.margin.app.ui.theme.FloatingAddButtonPadding
import com.margin.app.ui.theme.MarginRadius
import com.margin.app.ui.theme.SurfaceBorder
import com.margin.app.ui.theme.SurfaceElevated1
import com.margin.app.ui.theme.TextSecondary

@Composable
fun MoreScreen(
    onNavigateToAnalytics: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToKpiDetail: ((String) -> Unit)? = null,
    onExportCsv: (() -> Unit)? = null
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 20.dp,
            top = 24.dp,
            end = 20.dp,
            bottom = FloatingAddButtonPadding
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column {
                Text(text = "More", style = MaterialTheme.typography.displayLarge)
                Text(
                    text = "Tools, reports, and settings",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondary,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Screens section
        item {
            Text(
                text = "Screens",
                style = MaterialTheme.typography.titleMedium,
                color = TextSecondary,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        item {
            MoreTile(
                icon = Icons.Filled.Analytics,
                title = "Analytics",
                subtitle = "Profit trends, category breakdowns, and KPIs",
                onClick = onNavigateToAnalytics
            )
        }

        item {
            MoreTile(
                icon = Icons.Filled.Settings,
                title = "Settings",
                subtitle = "Theme, accent color, and preferences",
                onClick = onNavigateToSettings
            )
        }

        // Data section
        if (onExportCsv != null) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Text(
                    text = "Data",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextSecondary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            item {
                MoreTile(
                    icon = Icons.Filled.FileDownload,
                    title = "Export as CSV",
                    subtitle = "Export all inventory items to a spreadsheet",
                    onClick = onExportCsv
                )
            }
        }

        // Reports section
        if (onNavigateToKpiDetail != null) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Text(
                    text = "Reports",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextSecondary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            item {
                MoreTile(
                    icon = Icons.AutoMirrored.Filled.TrendingUp,
                    title = "Profit Detail",
                    subtitle = "Net profit trend with line chart",
                    onClick = { onNavigateToKpiDetail("profit") }
                )
            }
            item {
                MoreTile(
                    icon = Icons.Filled.AttachMoney,
                    title = "Revenue Detail",
                    subtitle = "Revenue from sold items over time",
                    onClick = { onNavigateToKpiDetail("revenue") }
                )
            }
            item {
                MoreTile(
                    icon = Icons.Filled.Savings,
                    title = "Investment Detail",
                    subtitle = "Capital tied up in active inventory",
                    onClick = { onNavigateToKpiDetail("invested") }
                )
            }
            item {
                MoreTile(
                    icon = Icons.Filled.PieChart,
                    title = "ROI Detail",
                    subtitle = "Return on investment breakdown",
                    onClick = { onNavigateToKpiDetail("roi") }
                )
            }
            item {
                MoreTile(
                    icon = Icons.Filled.Schedule,
                    title = "Days to Sell",
                    subtitle = "Average time to close a flip",
                    onClick = { onNavigateToKpiDetail("avgDays") }
                )
            }
        }
    }
}

@Composable
private fun MoreTile(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceElevated1, MarginRadius.Card)
            .border(1.dp, SurfaceBorder, MarginRadius.Card)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(20.dp)
        )
    }
}
