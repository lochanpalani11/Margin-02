package com.margin.app.ui.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

/**
 * Detects a swipe from the left edge of the screen (leftmost 40dp) and triggers [onBack].
 * The user must drag rightward from the edge zone by at least 80dp.
 *
 * Usage: Modifier.swipeBack { onBack() }
 */
fun Modifier.swipeBack(onBack: () -> Unit): Modifier = this.pointerInput(Unit) {
    val edgeZonePx = 40.dp.toPx()
    val thresholdPx = 80.dp.toPx()
    var totalDrag = 0f
    var inEdgeZone = false

    awaitPointerEventScope {
        while (true) {
            val event = awaitPointerEvent()
            val change = event.changes.firstOrNull() ?: continue

            when {
                change.pressed && !change.previousPressed -> {
                    inEdgeZone = change.position.x <= edgeZonePx
                    totalDrag = 0f
                    if (inEdgeZone) change.consume()
                }
                change.pressed && inEdgeZone -> {
                    totalDrag += change.position.x - change.previousPosition.x
                    change.consume()
                    if (totalDrag >= thresholdPx) {
                        inEdgeZone = false
                        onBack()
                    }
                }
                !change.pressed -> {
                    inEdgeZone = false
                    totalDrag = 0f
                }
            }
        }
    }
}
