package com.margin.app.ui.theme

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.themePreferences by preferencesDataStore(name = "theme_settings")

object ThemeKeys {
    val THEME_MODE = intPreferencesKey("theme_mode") // 0=Dark, 1=Light, 2=System
    val ACCENT_INDEX = intPreferencesKey("accent_index")
}

enum class ThemeMode(val value: Int) {
    DARK(0),
    LIGHT(1),
    SYSTEM(2);

    companion object {
        fun fromValue(value: Int): ThemeMode = entries.firstOrNull { it.value == value } ?: DARK
    }
}

fun themeModeFlow(context: Context): Flow<ThemeMode> =
    context.themePreferences.data.map { prefs ->
        ThemeMode.fromValue(prefs[ThemeKeys.THEME_MODE] ?: 0)
    }

fun accentIndexFlow(context: Context): Flow<Int> =
    context.themePreferences.data.map { prefs ->
        (prefs[ThemeKeys.ACCENT_INDEX] ?: 0).coerceIn(0, AccentOptions.lastIndex)
    }
