package com.margin.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.margin.app.domain.model.ProfitPoint
import com.margin.app.ui.theme.LossRed
import com.margin.app.ui.theme.ProfitGreen
import com.margin.app.ui.theme.TextTertiary
import com.margin.app.ui.util.formatSignedCurrency
import kotlin.math.abs
import kotlin.math.max

/**
 * A minimal, dependency-free bar chart for monthly profit. Built directly on
 * Compose's Canvas so the visual language stays fully in our control.
 * Supports tap and horizontal drag to inspect exact values at each bar
 * with a floating tooltip — without blocking vertical scrolling.
 */
@Composable
fun ProfitBarChart(
    points: List<ProfitPoint>,
    modifier: Modifier = Modifier,
    barColorPositive: Color = ProfitGreen,
    barColorNegative: Color = LossRed
) {
    val maxAbs = max(points.maxOfOrNull { abs(it.profit) } ?: 0.0, 1.0)
    val textMeasurer = rememberTextMeasurer()
    var selectedIndex by remember { mutableIntStateOf(-1) }
    var canvasWidth by remember { mutableStateOf(0f) }

    val outlineColor = MaterialTheme.colorScheme.outline
    val tooltipStyle = TextStyle(
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 12.sp,
        lineHeight = 16.sp
    )
    val tooltipBackgroundColor = MaterialTheme.colorScheme.surfaceVariant

    Column(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                // Tap to show / toggle tooltip without blocking scroll
                .pointerInput(points) {
                    detectTapGestures { offset ->
                        val barSlot = canvasWidth / points.size.coerceAtLeast(1).toFloat()
                        val idx = (offset.x / barSlot).toInt()
                            .coerceIn(0, (points.size - 1).coerceAtLeast(0))
                        selectedIndex = if (selectedIndex == idx) -1 else idx
                    }
                }
                // Horizontal drag to scrub across bars — vertical drags pass through
                .pointerInput(points) {
                    detectHorizontalDragGestures(
                        onDragStart = { offset ->
                            val barSlot = canvasWidth / points.size.coerceAtLeast(1).toFloat()
                            selectedIndex = (offset.x / barSlot).toInt()
                                .coerceIn(0, (points.size - 1).coerceAtLeast(0))
                        },
                        onHorizontalDrag = { change, _ ->
                            val barSlot = canvasWidth / points.size.coerceAtLeast(1).toFloat()
                            selectedIndex = (change.position.x / barSlot).toInt()
                                .coerceIn(0, (points.size - 1).coerceAtLeast(0))
                        },
                        onDragEnd = { selectedIndex = -1 },
                        onDragCancel = { selectedIndex = -1 }
                    )
                }
        ) {
            canvasWidth = size.width
            if (points.isEmpty()) return@Canvas
            val barSlot = size.width / points.size
            val barWidth = barSlot * 0.5f
            val zeroY = size.height / 2f
            val chartTop = 8.dp.toPx()
            val chartBottom = size.height - 8.dp.toPx()

            // Baseline
            drawLine(
                color = outlineColor,
                start = Offset(0f, zeroY),
                end = Offset(size.width, zeroY),
                strokeWidth = 1.dp.toPx()
            )

            points.forEachIndexed { index, point ->
                val ratio = (point.profit / maxAbs).toFloat().coerceIn(-1f, 1f)
                val barHeight = abs(ratio) * (zeroY - chartTop)
                val left = index * barSlot + (barSlot - barWidth) / 2f
                val top = if (ratio >= 0) zeroY - barHeight else zeroY
                val barColor = if (ratio >= 0) barColorPositive else barColorNegative
                val isSelected = index == selectedIndex
                val hasSelection = selectedIndex in points.indices

                drawRoundRect(
                    color = if (hasSelection && !isSelected) barColor.copy(alpha = 0.7f) else barColor,
                    topLeft = Offset(left, top),
                    size = Size(barWidth, max(barHeight, 3f)),
                    cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx())
                )
            }

            // Draw selected bar indicator
            if (selectedIndex in points.indices) {
                val point = points[selectedIndex]
                val centerX = selectedIndex * barSlot + barSlot / 2f
                val ratio = (point.profit / maxAbs).toFloat().coerceIn(-1f, 1f)
                val barHeight = abs(ratio) * (zeroY - chartTop)
                val barTop = if (ratio >= 0) zeroY - barHeight else zeroY
                val barBottom = if (ratio >= 0) zeroY else zeroY + barHeight
                val anchorY = if (ratio >= 0) barTop else barBottom

                // Vertical indicator line
                drawLine(
                    color = barColorPositive.copy(alpha = 0.5f),
                    start = Offset(centerX, chartTop),
                    end = Offset(centerX, chartBottom),
                    strokeWidth = 1.dp.toPx()
                )

                // Tooltip
                val tooltipText = "${point.periodLabel}\n${formatSignedCurrency(point.profit)}"
                val measuredText = textMeasurer.measure(
                    text = tooltipText,
                    style = tooltipStyle
                )
                val tooltipWidth = measuredText.size.width + 24.dp.toPx()
                val tooltipHeight = measuredText.size.height + 16.dp.toPx()

                // Position tooltip above the bar top (or below for negative bars), clamped to screen
                val tooltipX = (centerX - tooltipWidth / 2f).coerceIn(0f, size.width - tooltipWidth)
                val tooltipY = if (ratio >= 0) {
                    (anchorY - tooltipHeight - 8.dp.toPx()).coerceAtLeast(0f)
                } else {
                    (anchorY + 8.dp.toPx()).coerceAtMost(size.height - tooltipHeight)
                }

                // Tooltip background
                drawRoundRect(
                    color = tooltipBackgroundColor,
                    topLeft = Offset(tooltipX, tooltipY),
                    size = Size(tooltipWidth, tooltipHeight),
                    cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                )

                // Tooltip text
                drawText(
                    textMeasurer = textMeasurer,
                    text = tooltipText,
                    style = tooltipStyle,
                    topLeft = Offset(
                        tooltipX + 12.dp.toPx(),
                        tooltipY + 8.dp.toPx()
                    )
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            points.forEach { point ->
                Text(
                    text = point.periodLabel,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextTertiary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Selected bar summary label
        if (selectedIndex in points.indices) {
            val selected = points[selectedIndex]
            Text(
                text = "${selected.periodLabel}: ${formatSignedCurrency(selected.profit)}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }
    }
}
