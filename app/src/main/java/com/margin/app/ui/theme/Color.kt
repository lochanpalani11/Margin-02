package com.margin.app.ui.theme

import androidx.compose.ui.graphics.Color

// Surfaces — Dark (near-black, layered elevation like Linear/Raycast)
val SurfaceBaseDark = Color(0xFF0A0A0B)
val SurfaceElevated1Dark = Color(0xFF141416)
val SurfaceElevated2Dark = Color(0xFF1C1C1F)
val SurfaceElevated3Dark = Color(0xFF242428)
val SurfaceBorderDark = Color(0xFF2A2A2E)

// Surfaces — Light
val SurfaceBaseLight = Color(0xFFF9F9FB)
val SurfaceElevated1Light = Color(0xFFFFFFFF)
val SurfaceElevated2Light = Color(0xFFF3F3F6)
val SurfaceElevated3Light = Color(0xFFEAEAEE)
val SurfaceBorderLight = Color(0xFFE0E0E4)

// Text — Dark (brightened for better readability)
val TextPrimaryDark = Color(0xFFFFFFFF)
val TextSecondaryDark = Color(0xFFB8B8C0)
val TextTertiaryDark = Color(0xFF82828A)

// Text — Light
val TextPrimaryLight = Color(0xFF1A1A1E)
val TextSecondaryLight = Color(0xFF6B6B71)
val TextTertiaryLight = Color(0xFF9D9DA6)

// Semantic
val ProfitGreen = Color(0xFF4ADE80)
val LossRed = Color(0xFFF87171)
val NeutralAmber = Color(0xFFFBBF24)

// Accent color options users can pick from
data class AccentOption(val name: String, val primary: Color, val bright: Color, val dim: Color)

val AccentOptions = listOf(
    AccentOption("Green", Color(0xFF4ADE80), Color(0xFF7CF29C), Color(0xFF1F6B3C)),
    AccentOption("Purple", Color(0xFFA78BFA), Color(0xFFC4B5FD), Color(0xFF5B21B6)),
    AccentOption("Blue", Color(0xFF60A5FA), Color(0xFF93C5FD), Color(0xFF1E40AF)),
    AccentOption("Amber", Color(0xFFFBBF24), Color(0xFFFCD34D), Color(0xFFB45309)),
    AccentOption("Rose", Color(0xFFFB7185), Color(0xFFFDA4AF), Color(0xFFBE123C)),
    AccentOption("Teal", Color(0xFF2DD4BF), Color(0xFF5EEAD4), Color(0xFF0F766E)),
)

// Status chip colors
val StatusInStockBg = Color(0xFF1E293B)
val StatusInStockFg = Color(0xFF60A5FA)
val StatusListedBg = Color(0xFF3A2E1E)
val StatusListedFg = Color(0xFFFBBF24)
val StatusSoldBg = Color(0xFF16321F)
val StatusSoldFg = Color(0xFF4ADE80)
