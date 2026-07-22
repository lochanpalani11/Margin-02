package com.margin.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val MarginShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

// Additional radii used directly by custom components (cards, chips, tiles)
object MarginRadius {
    val Chip = RoundedCornerShape(100)
    val Card = RoundedCornerShape(20.dp)
    val Tile = RoundedCornerShape(18.dp)
    val Sheet = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
}
