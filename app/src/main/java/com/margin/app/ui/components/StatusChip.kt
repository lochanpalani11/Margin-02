package com.margin.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.margin.app.domain.model.ItemStatus
import com.margin.app.ui.theme.MarginRadius
import com.margin.app.ui.theme.StatusInStockBg
import com.margin.app.ui.theme.StatusInStockFg
import com.margin.app.ui.theme.StatusListedBg
import com.margin.app.ui.theme.StatusListedFg
import com.margin.app.ui.theme.StatusSoldBg
import com.margin.app.ui.theme.StatusSoldFg

@Composable
fun StatusChip(status: ItemStatus, modifier: Modifier = Modifier) {
    val (bg, fg, label) = when (status) {
        ItemStatus.IN_STOCK -> Triple(StatusInStockBg, StatusInStockFg, "In Stock")
        ItemStatus.LISTED -> Triple(StatusListedBg, StatusListedFg, "Listed")
        ItemStatus.SOLD -> Triple(StatusSoldBg, StatusSoldFg, "Sold")
    }
    Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        color = fg,
        modifier = modifier
            .background(bg, MarginRadius.Chip)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}
