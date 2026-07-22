package com.margin.app.ui.screens.itemdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.margin.app.domain.model.ItemStatus
import com.margin.app.ui.components.ImageViewerDialog
import com.margin.app.ui.components.MarginPrimaryButton
import com.margin.app.ui.components.MarginSecondaryButton
import com.margin.app.ui.components.ProfitIndicator
import com.margin.app.ui.components.StatusChip
import com.margin.app.ui.components.swipeBack
import com.margin.app.ui.theme.MarginRadius
import com.margin.app.ui.theme.SurfaceElevated1
import com.margin.app.ui.theme.SurfaceElevated2
import com.margin.app.ui.theme.TextSecondary
import com.margin.app.ui.util.formatCurrency
import com.margin.app.ui.util.formatDate
import com.margin.app.ui.util.formatDays
import com.margin.app.ui.util.formatPercent

@Composable
fun ItemDetailScreen(
    onBack: () -> Unit,
    onEdit: (Long) -> Unit,
    onSell: (Long) -> Unit,
    viewModel: ItemDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDeleteConfirm by remember { mutableStateOf(false) }
    var showImageViewer by remember { mutableStateOf(false) }
    val item = uiState.item

    LaunchedEffect(uiState.isDeleted) {
        if (uiState.isDeleted) onBack()
    }

    if (showImageViewer && item?.imageUri != null) {
        ImageViewerDialog(imageUri = item.imageUri!!, onDismiss = { showImageViewer = false })
    }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Delete this item?") },
            text = { Text("This can't be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteConfirm = false
                    viewModel.delete()
                }) { Text("Delete") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) { Text("Cancel") }
            }
        )
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize().swipeBack(onBack),
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            TopAppBar(
                title = { Text("Item Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    uiState.item?.let { item ->
                        IconButton(onClick = { onEdit(item.id) }) {
                            Icon(Icons.Filled.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = { showDeleteConfirm = true }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Delete")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        if (item == null) {
            if (!uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Text("Item not found", color = TextSecondary)
                }
            }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(
                horizontal = 20.dp,
                vertical = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(SurfaceElevated2)
                            .then(
                                if (item.imageUri != null) Modifier.clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { showImageViewer = true }
                                else Modifier
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (item.imageUri != null) {
                            AsyncImage(
                                model = item.imageUri,
                                contentDescription = item.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(RoundedCornerShape(18.dp))
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.Inventory2,
                                contentDescription = null,
                                tint = TextSecondary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = item.name, style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            StatusChip(status = item.status)
                            Text(
                                text = item.category,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary
                            )
                        }
                        // Haul link chip
                        if (uiState.haulName != null) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.LocalShipping,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = uiState.haulName!!,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }

            if (item.status == ItemStatus.SOLD && item.profit != null) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SurfaceElevated1, MarginRadius.Card)
                            .padding(16.dp)
                    ) {
                        Text(text = "Profit", style = MaterialTheme.typography.labelMedium, color = TextSecondary)
                        Spacer(modifier = Modifier.height(6.dp))
                        ProfitIndicator(amount = item.profit!!, style = MaterialTheme.typography.headlineLarge)
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                            LabeledStat(label = "Margin", value = formatPercent(item.marginPercent ?: 0.0))
                            LabeledStat(label = "ROI", value = formatPercent(item.roiPercent ?: 0.0))
                            LabeledStat(label = "Held", value = formatDays(item.daysHeld))
                        }
                    }
                }
            }

            item {
                DetailSection(title = "Purchase") {
                    DetailRow(label = "Price", value = formatCurrency(item.purchasePrice))
                    DetailRow(label = "Date", value = formatDate(item.purchaseDate))
                    DetailRow(label = "From", value = item.purchasePlatform.ifBlank { "—" })
                }
            }

            if (item.status == ItemStatus.SOLD) {
                item {
                    DetailSection(title = "Sale") {
                        DetailRow(label = "Price", value = item.salePrice?.let { formatCurrency(it) } ?: "—")
                        DetailRow(label = "Fees", value = formatCurrency(item.saleFees))
                        DetailRow(label = "Date", value = item.saleDate?.let { formatDate(it) } ?: "—")
                        DetailRow(label = "Platform", value = item.salePlatform?.ifBlank { "—" } ?: "—")
                    }
                }
            }

            if (item.notes.isNotBlank()) {
                item {
                    DetailSection(title = "Notes") {
                        Text(
                            text = item.notes,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                }
            }

            if (item.status != ItemStatus.SOLD) {
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    MarginPrimaryButton(text = "Mark as Sold", onClick = { onSell(item.id) })
                    Spacer(modifier = Modifier.height(8.dp))
                    if (item.status == ItemStatus.IN_STOCK) {
                        MarginSecondaryButton(text = "Mark as Listed", onClick = viewModel::markAsListed)
                    }
                }
            }
        }
    }
}

@Composable
private fun LabeledStat(label: String, value: String) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = TextSecondary)
        Text(text = value, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
private fun DetailSection(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceElevated1, MarginRadius.Card)
            .padding(16.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(10.dp))
        content()
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}
