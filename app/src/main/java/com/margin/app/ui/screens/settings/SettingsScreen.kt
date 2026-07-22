package com.margin.app.ui.screens.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.margin.app.ui.components.MarginSecondaryButton
import com.margin.app.ui.theme.AccentOptions
import com.margin.app.ui.theme.MarginRadius
import com.margin.app.ui.theme.SurfaceBorder
import com.margin.app.ui.theme.SurfaceElevated1
import com.margin.app.ui.theme.TextSecondary
import com.margin.app.ui.theme.FloatingAddButtonPadding
import com.margin.app.ui.theme.ThemeMode

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var showClearConfirm by remember { mutableStateOf(false) }
    var showImportConfirm by remember { mutableStateOf<Uri?>(null) }

    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json")
    ) { uri -> uri?.let(viewModel::exportData) }

    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri -> uri?.let { showImportConfirm = it } }

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.messageShown()
        }
    }

    if (showClearConfirm) {
        AlertDialog(
            onDismissRequest = { showClearConfirm = false },
            title = { Text("Clear all data?") },
            text = { Text("This permanently deletes every item in your inventory. This can't be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    showClearConfirm = false
                    viewModel.clearAllData()
                }) { Text("Clear Everything") }
            },
            dismissButton = {
                TextButton(onClick = { showClearConfirm = false }) { Text("Cancel") }
            }
        )
    }

    showImportConfirm?.let { uri ->
        AlertDialog(
            onDismissRequest = { showImportConfirm = null },
            title = { Text("Replace current data?") },
            text = { Text("Importing will replace everything currently in your inventory with the contents of this backup file.") },
            confirmButton = {
                TextButton(onClick = {
                    showImportConfirm = null
                    viewModel.importData(uri)
                }) { Text("Import") }
            },
            dismissButton = {
                TextButton(onClick = { showImportConfirm = null }) { Text("Cancel") }
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(
                start = 20.dp,
                top = 24.dp,
                end = 20.dp,
                bottom = FloatingAddButtonPadding
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Appearance section
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceElevated1, MarginRadius.Card)
                        .padding(16.dp)
                ) {
                    Text(text = "Appearance", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(12.dp))

                    // Theme mode selector
                    Text(
                        text = "Theme",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        ThemeMode.entries.forEach { mode ->
                            val isSelected = uiState.themeMode == mode
                            Box(
                                modifier = Modifier
                                    .clip(MarginRadius.Chip)
                                    .background(
                                        if (isSelected) MaterialTheme.colorScheme.primary
                                        else SurfaceElevated1
                                    )
                                    .border(
                                        1.dp,
                                        if (isSelected) MaterialTheme.colorScheme.primary
                                        else SurfaceBorder,
                                        MarginRadius.Chip
                                    )
                                    .clickable { viewModel.onThemeModeChanged(mode) }
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = when (mode) {
                                        ThemeMode.DARK -> "Dark"
                                        ThemeMode.LIGHT -> "Light"
                                        ThemeMode.SYSTEM -> "System"
                                    },
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                                            else TextSecondary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Accent color selector
                    Text(
                        text = "Accent Color",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        AccentOptions.forEachIndexed { index, accent ->
                            val isSelected = uiState.accentIndex == index
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(accent.primary)
                                    .then(
                                        if (isSelected) Modifier.border(3.dp, MaterialTheme.colorScheme.onSurface, CircleShape)
                                        else Modifier.border(1.dp, SurfaceBorder, CircleShape)
                                    )
                                    .clickable { viewModel.onAccentChanged(index) }
                            )
                        }
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceElevated1, MarginRadius.Card)
                        .padding(16.dp)
                ) {
                    Text(text = "Your data", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "Margin stores everything on this device only. No account, no cloud, no internet connection required.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                    )
                    MarginSecondaryButton(
                        text = "Export Backup (JSON)",
                        onClick = { exportLauncher.launch(viewModel.defaultExportFileName()) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    MarginSecondaryButton(
                        text = "Import Backup (JSON)",
                        onClick = { importLauncher.launch(arrayOf("application/json")) }
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceElevated1, MarginRadius.Card)
                        .padding(16.dp)
                ) {
                    Text(text = "Danger zone", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "Permanently delete every item in your inventory.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                    )
                    MarginSecondaryButton(
                        text = "Clear All Data",
                        onClick = { showClearConfirm = true }
                    )
                }
            }

            item {
                Text(
                    text = "Margin v2.0.0",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }
    }
}
