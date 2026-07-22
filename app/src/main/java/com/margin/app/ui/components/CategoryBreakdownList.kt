package com.margin.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.margin.app.domain.model.CategoryBreakdown
import com.margin.app.ui.theme.AccentGreen
import com.margin.app.ui.theme.SurfaceElevated2
import com.margin.app.ui.theme.TextSecondary
import com.margin.app.ui.util.formatCurrency
import kotlin.math.max

@Composable
fun CategoryBreakdownList(
    categories: List<CategoryBreakdown>,
    modifier: Modifier = Modifier
) {
    val maxProfit = max(categories.maxOfOrNull { it.totalProfit } ?: 0.0, 1.0)

    Column(modifier = modifier) {
        categories.forEach { category ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = category.category,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = formatCurrency(category.totalProfit),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(6.dp))
                    val fraction = (category.totalProfit / maxProfit).toFloat().coerceIn(0f, 1f)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .background(SurfaceElevated2, RoundedCornerShape(100))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(fraction)
                                .height(6.dp)
                                .background(AccentGreen, RoundedCornerShape(100))
                        )
                    }
                }
            }
        }
        if (categories.isEmpty()) {
            Text(
                text = "No sold items yet — analytics will appear here once you close out a flip.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }
    }
}
