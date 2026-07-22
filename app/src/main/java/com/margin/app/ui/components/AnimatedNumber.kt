package com.margin.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.margin.app.ui.util.formatCurrency
import com.margin.app.ui.util.formatPercent
import kotlin.math.roundToInt

/** Smoothly animates a currency value when it changes. */
@Composable
fun AnimatedCurrencyText(
    amount: Double,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.headlineMedium,
    animationSpec: androidx.compose.animation.core.AnimationSpec<Float> = tween(600)
) {
    val animated by animateFloatAsState(
        targetValue = amount.toFloat(),
        animationSpec = animationSpec,
        label = "animatedCurrency"
    )
    Text(
        text = formatCurrency(animated.toDouble()),
        style = style,
        modifier = modifier
    )
}

/** Smoothly animates a percentage value when it changes. */
@Composable
fun AnimatedPercentText(
    percent: Double,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.headlineMedium,
    animationSpec: androidx.compose.animation.core.AnimationSpec<Float> = tween(600)
) {
    val animated by animateFloatAsState(
        targetValue = percent.toFloat(),
        animationSpec = animationSpec,
        label = "animatedPercent"
    )
    Text(
        text = formatPercent(animated.toDouble()),
        style = style,
        modifier = modifier
    )
}

/** Smoothly animates an integer count when it changes. */
@Composable
fun AnimatedCountText(
    count: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.headlineMedium,
    animationSpec: androidx.compose.animation.core.AnimationSpec<Float> = tween(600)
) {
    val animated by animateFloatAsState(
        targetValue = count.toFloat(),
        animationSpec = animationSpec,
        label = "animatedCount"
    )
    Text(
        text = "${animated.roundToInt()}",
        style = style,
        modifier = modifier
    )
}

/** A ProfitIndicator that smoothly animates its profit value. */
@Composable
fun AnimatedProfitIndicator(
    amount: Double,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.headlineMedium,
    showIcon: Boolean = true,
    animationSpec: androidx.compose.animation.core.AnimationSpec<Float> = tween(600)
) {
    val animated by animateFloatAsState(
        targetValue = amount.toFloat(),
        animationSpec = animationSpec,
        label = "animatedProfit"
    )
    ProfitIndicator(
        amount = animated.toDouble(),
        modifier = modifier,
        style = style,
        showIcon = showIcon
    )
}
