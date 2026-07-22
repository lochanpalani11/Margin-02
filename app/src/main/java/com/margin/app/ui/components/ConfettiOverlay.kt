package com.margin.app.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private val confettiColors = listOf(
    Color(0xFF4ADE80), // green
    Color(0xFF60A5FA), // blue
    Color(0xFFFBBF24), // amber
    Color(0xFFFB7185), // rose
    Color(0xFFA78BFA), // purple
    Color(0xFF2DD4BF), // teal
    Color(0xFFFFA726), // orange
    Color(0xFF42A5F5), // light blue
)

private data class Particle(
    val angle: Float,
    val speed: Float,
    val size: Float,
    val color: Color
)

@Composable
fun ConfettiOverlay(
    modifier: Modifier = Modifier,
    particleCount: Int = 40
) {
    val particles = remember {
        List(particleCount) {
            Particle(
                angle = Random.nextFloat() * 360f,
                speed = Random.nextFloat() * 0.6f + 0.4f,
                size = Random.nextFloat() * 10f + 6f,
                color = confettiColors[Random.nextInt(confettiColors.size)]
            )
        }
    }

    val progress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1200)
        )
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val cx = size.width / 2
        val cy = size.height / 2
        val maxDist = size.width.coerceAtLeast(size.height) * 0.7f
        val p = progress.value

        particles.forEach { particle ->
            val easeOut = 1f - (1f - p) * (1f - p)
            val dist = maxDist * particle.speed * easeOut
            val x = cx + dist * cos(Math.toRadians(particle.angle.toDouble())).toFloat()
            val y = cy + dist * sin(Math.toRadians(particle.angle.toDouble())).toFloat()
            val alpha = (1f - p).coerceIn(0f, 1f)
            val currentSize = particle.size * (1f + p * 0.5f)

            drawCircle(
                color = particle.color.copy(alpha = alpha),
                radius = currentSize,
                center = Offset(x, y),
                style = Fill
            )
        }
    }
}
