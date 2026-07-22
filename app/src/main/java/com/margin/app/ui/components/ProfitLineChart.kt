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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.margin.app.domain.model.ProfitPoint
import com.margin.app.ui.theme.ProfitGreen
import com.margin.app.ui.theme.TextTertiary
import com.margin.app.ui.util.formatSignedCurrency
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * A stock-market-style line chart for profit over time. Renders a smooth
 * line with gradient fill underneath, evoking a financial dashboard feel.
 * Supports tap and horizontal drag to inspect exact values at each point
 * with a floating tooltip — without blocking vertical scrolling.
 */
@Composable
fun ProfitLineChart(
    points: List<ProfitPoint>,
    modifier: Modifier = Modifier,
    lineColor: Color = ProfitGreen,
    fillColor: Color = ProfitGreen.copy(alpha = 0.15f)
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
                .height(200.dp)
                // Tap to show / toggle tooltip without blocking scroll
                .pointerInput(points) {
                    detectTapGestures { offset ->
                        val stepX = canvasWidth / (points.size - 1).coerceAtLeast(1).toFloat()
                        val idx = ((offset.x / stepX) + 0.5f).roundToInt()
                            .coerceIn(0, (points.size - 1).coerceAtLeast(0))
                        selectedIndex = if (selectedIndex == idx) -1 else idx
                    }
                }
                // Horizontal drag to scrub across points — vertical drags pass through
                .pointerInput(points) {
                    detectHorizontalDragGestures(
                        onDragStart = { offset ->
                            val stepX = canvasWidth / (points.size - 1).coerceAtLeast(1).toFloat()
                            selectedIndex = ((offset.x / stepX) + 0.5f).roundToInt()
                                .coerceIn(0, (points.size - 1).coerceAtLeast(0))
                        },
                        onHorizontalDrag = { change, _ ->
                            val stepX = canvasWidth / (points.size - 1).coerceAtLeast(1).toFloat()
                            selectedIndex = ((change.position.x / stepX) + 0.5f).roundToInt()
                                .coerceIn(0, (points.size - 1).coerceAtLeast(0))
                        },
                        onDragEnd = { selectedIndex = -1 },
                        onDragCancel = { selectedIndex = -1 }
                    )
                }
        ) {
            canvasWidth = size.width
            if (points.size < 2) return@Canvas
            val zeroY = size.height / 2f
            val stepX = size.width / (points.size - 1).toFloat()
            val chartTop = 20.dp.toPx()
            val chartBottom = size.height - 20.dp.toPx()

            // Baseline
            drawLine(
                color = outlineColor,
                start = Offset(0f, zeroY),
                end = Offset(size.width, zeroY),
                strokeWidth = 1.dp.toPx()
            )

            val path = Path()
            val fillPath = Path()

            points.forEachIndexed { index, point ->
                val x = index * stepX
                val ratio = (point.profit / maxAbs).toFloat().coerceIn(-1f, 1f)
                val y = zeroY - ratio * (zeroY - chartTop)

                if (index == 0) {
                    path.moveTo(x, y)
                    fillPath.moveTo(x, zeroY)
                    fillPath.lineTo(x, y)
                } else {
                    path.lineTo(x, y)
                    fillPath.lineTo(x, y)
                }
            }

            // Close the fill path back to baseline
            fillPath.lineTo((points.size - 1) * stepX, zeroY)
            fillPath.close()

            // Draw fill
            drawPath(path = fillPath, color = fillColor)

            // Draw line
            drawPath(
                path = path,
                color = lineColor,
                style = Stroke(
                    width = 2.5.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )

            // Draw dots at each point
            points.forEachIndexed { index, point ->
                val x = index * stepX
                val ratio = (point.profit / maxAbs).toFloat().coerceIn(-1f, 1f)
                val y = zeroY - ratio * (zeroY - chartTop)
                drawCircle(
                    color = lineColor,
                    radius = 4.dp.toPx(),
                    center = Offset(x, y)
                )
            }

            // Draw selected point indicator
            if (selectedIndex in points.indices) {
                val point = points[selectedIndex]
                val selX = selectedIndex * stepX
                val ratio = (point.profit / maxAbs).toFloat().coerceIn(-1f, 1f)
                val selY = zeroY - ratio * (zeroY - chartTop)

                // Vertical indicator line
                drawLine(
                    color = lineColor.copy(alpha = 0.5f),
                    start = Offset(selX, chartTop),
                    end = Offset(selX, chartBottom),
                    strokeWidth = 1.dp.toPx()
                )

                // Larger highlighted dot
                drawCircle(
                    color = lineColor,
                    radius = 6.dp.toPx(),
                    center = Offset(selX, selY)
                )
                drawCircle(
                    color = Color.White.copy(alpha = 0.8f),
                    radius = 3.dp.toPx(),
                    center = Offset(selX, selY)
                )

                // Tooltip
                val tooltipText = "${point.periodLabel}\n${formatSignedCurrency(point.profit)}"
                val measuredText = textMeasurer.measure(
                    text = tooltipText,
                    style = tooltipStyle
                )
                val tooltipWidth = measuredText.size.width + 24.dp.toPx()
                val tooltipHeight = measuredText.size.height + 16.dp.toPx()

                // Position tooltip above the point, clamped to stay on screen
                val tooltipX = (selX - tooltipWidth / 2f).coerceIn(0f, size.width - tooltipWidth)
                val tooltipY = (selY - tooltipHeight - 12.dp.toPx()).coerceAtLeast(0f)

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
            // Show first, middle, and last labels
            if (points.isNotEmpty()) {
                Text(
                    text = points.first().periodLabel,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextTertiary
                )
                if (points.size >= 3) {
                    Text(
                        text = points[points.size / 2].periodLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextTertiary
                    )
                }
                if (points.size >= 2) {
                    Text(
                        text = points.last().periodLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextTertiary
                    )
                }
            }
        }
    }
}
