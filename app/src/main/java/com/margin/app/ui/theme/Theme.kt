package com.margin.app.ui.theme

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private fun buildDarkScheme(accent: AccentOption) = darkColorScheme(
    primary = accent.primary,
    onPrimary = Color.White,
    primaryContainer = accent.dim,
    onPrimaryContainer = accent.bright,
    secondary = TextSecondaryDark,
    onSecondary = Color.White,
    background = SurfaceBaseDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceElevated1Dark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceElevated2Dark,
    onSurfaceVariant = TextSecondaryDark,
    outline = SurfaceBorderDark,
    error = LossRed,
    onError = Color.White
)

private fun buildLightScheme(accent: AccentOption) = lightColorScheme(
    primary = accent.primary,
    onPrimary = Color.White,
    primaryContainer = accent.dim,
    onPrimaryContainer = accent.bright,
    secondary = TextSecondaryLight,
    onSecondary = SurfaceBaseLight,
    background = SurfaceBaseLight,
    onBackground = TextPrimaryLight,
    surface = SurfaceElevated1Light,
    onSurface = TextPrimaryLight,
    surfaceVariant = SurfaceElevated2Light,
    onSurfaceVariant = TextSecondaryLight,
    outline = SurfaceBorderLight,
    error = LossRed,
    onError = Color.White
)

// Backwards-compatible aliases so existing component imports still compile.
// @ReadOnlyComposable lets these work in composable, Canvas drawScope, and
// pointerInput contexts without requiring a @Composable call site.
val SurfaceBase: Color @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.background
val SurfaceElevated1: Color @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.surface
val SurfaceElevated2: Color @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.surfaceVariant
val SurfaceElevated3: Color @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.surfaceVariant
val SurfaceBorder: Color @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.outline
val AccentGreen: Color @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.primary
val AccentGreenBright: Color @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.primary
val AccentGreenDim: Color @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.primaryContainer
val TextPrimary: Color @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.onBackground
val TextSecondary: Color @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.onSurfaceVariant
val TextTertiary: Color @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f)

@Composable
fun MarginTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val themeMode by themeModeFlow(context).collectAsState(initial = ThemeMode.DARK)
    val accentIndex by accentIndexFlow(context).collectAsState(initial = 0)
    val accent = AccentOptions.getOrElse(accentIndex) { AccentOptions[0] }

    val useDarkTheme = when (themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val scheme = if (useDarkTheme) buildDarkScheme(accent) else buildLightScheme(accent)

    MaterialTheme(
        colorScheme = scheme,
        typography = MarginTypography,
        shapes = MarginShapes,
        content = content
    )
}
