package com.margin.app.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.margin.app.ui.theme.LossRed
import com.margin.app.ui.theme.NeutralAmber
import com.margin.app.ui.theme.ProfitGreen
import com.margin.app.ui.util.formatSignedCurrency

@Composable
fun ProfitIndicator(
    amount: Double,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    showIcon: Boolean = true
) {
    val color = when {
        amount > 0.0 -> ProfitGreen
        amount < 0.0 -> LossRed
        else -> NeutralAmber
    }
    val icon = when {
        amount > 0.0 -> Icons.Filled.ArrowUpward
        amount < 0.0 -> Icons.Filled.ArrowDownward
        else -> Icons.Filled.Remove
    }
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        if (showIcon) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(text = formatSignedCurrency(amount), style = style, color = color)
    }
}
