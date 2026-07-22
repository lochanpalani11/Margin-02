package com.margin.app.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.min

private fun rubberBand(raw: Float): Float = raw / (1f + raw / 180f)

@Composable
fun MarginPullRefresh(
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (Modifier) -> Unit
) {
    val density = LocalDensity.current
    val scope = rememberCoroutineScope()

    var isRefreshing by remember { mutableStateOf(false) }
    val pullOffset = remember { Animatable(0f) }
    val indicatorHeightPx = with(density) { 64.dp.toPx() }
    val refreshThresholdPx = with(density) { 56.dp.toPx() }

    val infiniteTransition = rememberInfiniteTransition(label = "pullRefreshSpin")
    val spinAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "spinAngle"
    )

    val pullConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (pullOffset.value > 0f && available.y < 0f) {
                    val consumed = min(pullOffset.value, -available.y)
                    scope.launch { pullOffset.snapTo(pullOffset.value - consumed) }
                    return Offset(0f, -consumed)
                }
                return Offset.Zero
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (available.y > 0f && source == NestedScrollSource.UserInput && !isRefreshing) {
                    val dampened = rubberBand(available.y)
                    val newOffset = (pullOffset.value + dampened).coerceAtMost(300f)
                    scope.launch { pullOffset.snapTo(newOffset) }
                    return Offset(0f, available.y)
                }
                return Offset.Zero
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                if (pullOffset.value > 0f) {
                    if (pullOffset.value >= refreshThresholdPx) {
                        isRefreshing = true
                        pullOffset.animateTo(indicatorHeightPx, spring(dampingRatio = 0.6f, stiffness = 300f))
                        onRefresh()
                        delay(1000)
                        isRefreshing = false
                    }
                    pullOffset.animateTo(0f, spring(dampingRatio = 0.6f, stiffness = 300f))
                    return available
                }
                return Velocity.Zero
            }
        }
    }

    Box(modifier = modifier.nestedScroll(pullConnection)) {
        // Spinning refresh indicator
        if (pullOffset.value > 0f || isRefreshing) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 52.dp)
                    .graphicsLayer {
                        translationY = -indicatorHeightPx + pullOffset.value.coerceAtMost(indicatorHeightPx)
                        alpha = (pullOffset.value / indicatorHeightPx).coerceIn(0f, 1f)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Pull to refresh",
                    modifier = Modifier
                        .size(28.dp)
                        .graphicsLayer {
                            rotationZ = if (isRefreshing) spinAngle
                                        else (pullOffset.value / refreshThresholdPx * 360f).coerceAtMost(360f)
                        },
                    tint = MaterialTheme.colorScheme.primary.copy(
                        alpha = (pullOffset.value / refreshThresholdPx).coerceIn(0.3f, 1f)
                    )
                )
            }
        }

        // Content with rubber-band displacement
        content(
            Modifier.graphicsLayer {
                translationY = pullOffset.value
            }
        )
    }
}
