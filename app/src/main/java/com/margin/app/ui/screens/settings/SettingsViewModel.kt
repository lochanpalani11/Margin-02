package com.margin.app.ui.screens.settings

import android.content.Context
import android.net.Uri
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margin.app.data.backup.BackupManager
import com.margin.app.domain.repository.ItemRepository
import com.margin.app.ui.theme.ThemeKeys
import com.margin.app.ui.theme.ThemeMode
import com.margin.app.ui.theme.accentIndexFlow
import com.margin.app.ui.theme.themeModeFlow
import com.margin.app.ui.theme.themePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val message: String? = null,
    val isWorking: Boolean = false,
    val themeMode: ThemeMode = ThemeMode.DARK,
    val accentIndex: Int = 0
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: ItemRepository,
    private val backupManager: BackupManager,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _message = MutableStateFlow<String?>(null)

    val uiState: StateFlow<SettingsUiState> = combine(
        themeModeFlow(context),
        accentIndexFlow(context),
        _message
    ) { themeMode, accentIndex, message ->
        SettingsUiState(
            message = message,
            themeMode = themeMode,
            accentIndex = accentIndex
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsUiState()
    )

    fun defaultExportFileName(): String = backupManager.defaultExportFileName()

    fun exportData(uri: Uri) {
        viewModelScope.launch {
            try {
                val items = repository.getAllItemsSnapshot()
                backupManager.exportToUri(uri, items)
                _message.value = "Exported ${items.size} items"
            } catch (e: Exception) {
                _message.value = "Export failed: ${e.message}"
            }
        }
    }

    fun importData(uri: Uri) {
        viewModelScope.launch {
            try {
                val items = backupManager.importFromUri(uri)
                repository.replaceAllItems(items)
                _message.value = "Imported ${items.size} items"
            } catch (e: Exception) {
                _message.value = "Import failed: ${e.message}"
            }
        }
    }

    fun clearAllData() {
        viewModelScope.launch {
            repository.replaceAllItems(emptyList())
            _message.value = "All data cleared"
        }
    }

    fun onThemeModeChanged(mode: ThemeMode) {
        viewModelScope.launch {
            context.themePreferences.edit { prefs ->
                prefs[ThemeKeys.THEME_MODE] = mode.value
            }
        }
    }

    fun onAccentChanged(index: Int) {
        viewModelScope.launch {
            context.themePreferences.edit { prefs ->
                prefs[ThemeKeys.ACCENT_INDEX] = index
            }
        }
    }

    fun messageShown() {
        _message.value = null
    }
}
