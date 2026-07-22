package com.margin.app.ui.screens.inventory

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import androidx.compose.ui.input.pointer.pointerInput
import com.margin.app.domain.model.InventoryItem
import com.margin.app.domain.model.ItemStatus
import com.margin.app.ui.theme.FloatingAddButtonPadding
import com.margin.app.ui.components.ConfettiOverlay
import com.margin.app.ui.components.EmptyState
import com.margin.app.ui.components.InventoryTile
import com.margin.app.ui.components.MarginPullRefresh
import com.margin.app.ui.components.MarginSearchField
import com.margin.app.ui.components.QuickSellSheet
import com.margin.app.ui.components.StatusChip
import com.margin.app.ui.theme.MarginRadius
import com.margin.app.ui.theme.SurfaceBorder
import com.margin.app.ui.theme.SurfaceElevated1
import com.margin.app.ui.theme.SurfaceElevated2
import com.margin.app.ui.theme.TextSecondary
import com.margin.app.ui.util.formatCurrency
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InventoryScreen(
    onItemClick: (Long) -> Unit,
    onAddItemClick: () -> Unit,
    onEditItem: ((Long) -> Unit)? = null,
    onSellItem: ((Long) -> Unit)? = null,
    onDeleteItem: ((Long) -> Unit)? = null,
    onBack: (() -> Unit)? = null,
    haulId: Long? = null,
    viewModel: InventoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var pressed by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    var quickSellItem by remember { mutableStateOf<InventoryItem?>(null) }

    val fabScale by animateFloatAsState(
        targetValue = if (pressed) 0.85f else 1f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 400f),
        label = "fabScale"
    )
    val checkScale by animateFloatAsState(
        targetValue = if (showSuccess) 1f else 0.3f,
        animationSpec = spring(dampingRatio = 0.4f, stiffness = 300f),
        label = "checkScale"
    )

    LaunchedEffect(showSuccess) {
        if (showSuccess) {
            delay(1500)
            showSuccess = false
        }
    }

    if (quickSellItem != null) {
        val item = quickSellItem!!
        QuickSellSheet(
            itemName = item.name,
            purchasePrice = item.purchasePrice,
            onConfirm = { salePrice, fees ->
                viewModel.quickSell(item.id, salePrice, fees)
                showSuccess = true
                quickSellItem = null
            },
            onDismiss = { quickSellItem = null }
        )
    }

    if (showSuccess) {
        ConfettiOverlay()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            if (haulId != null && onBack != null) {
                TopAppBar(
                    title = { Text("Haul Items") },
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
            }
        },
        floatingActionButton = {
            if (showSuccess) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .scale(checkScale)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Item saved",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(36.dp)
                    )
                }
            } else {
                FloatingActionButton(
                    onClick = {
                        pressed = true
                        onAddItemClick()
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.scale(fabScale)
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add item")
                }
            }
        }
    ) { padding ->
        MarginPullRefresh(
            onRefresh = { viewModel.refresh() },
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) { contentModifier ->
            LazyColumn(
                modifier = contentModifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 24.dp,
                    end = 20.dp,
                    bottom = FloatingAddButtonPadding
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = if (haulId != null) "Haul Items" else "Inventory",
                        style = MaterialTheme.typography.displayLarge
                    )
                }

                val displayItems = if (haulId != null) {
                    uiState.items.filter { it.haulId == haulId }
                } else {
                    uiState.items
                }

                // Haul summary
                if (haulId != null && displayItems.isNotEmpty()) {
                    val totalSpent = displayItems.sumOf { it.purchasePrice }
                    val totalProfit = displayItems.filter { it.status == ItemStatus.SOLD }.sumOf { it.profit ?: 0.0 }
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(SurfaceElevated1, MarginRadius.Tile)
                                .border(1.dp, SurfaceBorder, MarginRadius.Tile)
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Total Spent",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = TextSecondary
                                    )
                                    Text(
                                        text = formatCurrency(totalSpent),
                                        style = MaterialTheme.typography.headlineMedium
                                    )
                                }
                                Text(
                                    text = "${displayItems.size} items",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextSecondary
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Profit",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = TextSecondary
                                    )
                                    Text(
                                        text = formatCurrency(totalProfit),
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = if (totalProfit >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                                    )
                                }
                                val soldCount = displayItems.count { it.status == ItemStatus.SOLD }
                                Text(
                                    text = "$soldCount sold",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                }

                if (haulId == null) {
                    item {
                        MarginSearchField(
                            value = uiState.query,
                            onValueChange = viewModel::onQueryChange,
                            placeholder = "Search inventory"
                        )
                    }

                    item {
                        StatusFilterRow(
                            selected = uiState.statusFilter,
                            onSelect = viewModel::onStatusFilterChange
                        )
                    }

                    item {
                        SortRow(
                            selected = uiState.sortOption,
                            onSelect = viewModel::onSortChange
                        )
                    }
                }

                val listedItems = displayItems.filter { it.status == ItemStatus.LISTED }
                val inStockItems = displayItems.filter { it.status == ItemStatus.IN_STOCK }
                val soldItems = displayItems.filter { it.status == ItemStatus.SOLD }

                val showListed = uiState.statusFilter == null || uiState.statusFilter == ItemStatus.LISTED
                val showInStock = uiState.statusFilter == null || uiState.statusFilter == ItemStatus.IN_STOCK
                val showSold = uiState.statusFilter == null || uiState.statusFilter == ItemStatus.SOLD

                if (displayItems.isEmpty() && !uiState.isLoading) {
                    item {
                        EmptyState(
                            icon = Icons.Filled.Inventory2,
                            title = if (haulId != null) "No items in this haul" else "No items yet",
                            subtitle = if (haulId != null) "Add items to this haul using the + button."
                                       else "Tap the + button to add your first flip."
                        )
                    }
                } else {
                    if (showListed && listedItems.isNotEmpty()) {
                        item {
                            Text(
                                text = "Currently Listed (${listedItems.size})",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                        item {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(horizontal = 0.dp)
                            ) {
                                items(listedItems, key = { it.id }) { item ->
                                    ListedItemCard(
                                        item = item,
                                        onClick = { onItemClick(item.id) },
                                        onEdit = if (onEditItem != null) {{ onEditItem(item.id) }} else null,
                                        onSell = if (onSellItem != null) {{ onSellItem(item.id) }} else null,
                                        onDelete = if (onDeleteItem != null) {{ onDeleteItem(item.id) }} else null
                                    )
                                }
                            }
                        }
                    }

                    if (showInStock && inStockItems.isNotEmpty()) {
                        item {
                            Text(
                                text = "In Stock (${inStockItems.size})",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(top = if (showListed && listedItems.isNotEmpty()) 12.dp else 8.dp)
                            )
                        }
                        items(inStockItems, key = { it.id }) { item ->
                            InventoryTile(
                                item = item,
                                onClick = { onItemClick(item.id) },
                                onEdit = if (onEditItem != null) {{ onEditItem(item.id) }} else null,
                                onSell = if (onSellItem != null) {{ onSellItem(item.id) }} else null,
                                onQuickSell = { quickSellItem = item },
                                onMarkListed = {{ viewModel.markAsListed(item.id) }},
                                onDelete = if (onDeleteItem != null) {{ onDeleteItem(item.id) }} else null
                            )
                        }
                    }

                    if (showSold && soldItems.isNotEmpty()) {
                        item {
                            Text(
                                text = "Sold (${soldItems.size})",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(top = 12.dp)
                            )
                        }
                        items(soldItems, key = { it.id }) { item ->
                            InventoryTile(
                                item = item,
                                onClick = { onItemClick(item.id) },
                                onEdit = if (onEditItem != null) {{ onEditItem(item.id) }} else null,
                                onSell = null,
                                onMarkListed = null,
                                onDelete = if (onDeleteItem != null) {{ onDeleteItem(item.id) }} else null
                            )
                        }
                    }

                    if (showInStock && inStockItems.isEmpty() && !showSold && !showListed) {
                        item {
                            EmptyState(
                                icon = Icons.Filled.Inventory2,
                                title = "No items in stock",
                                subtitle = "All your items are either listed or sold."
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SortRow(
    selected: SortOption,
    onSelect: (SortOption) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Sort:",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        SortOption.entries.forEach { option ->
            FilterChip(
                selected = selected == option,
                onClick = { onSelect(option) },
                label = { Text(option.label) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                    selectedLabelColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@Composable
private fun StatusFilterRow(
    selected: ItemStatus?,
    onSelect: (ItemStatus?) -> Unit
) {
    val options = listOf<Pair<String, ItemStatus?>>(
        "All" to null,
        "In Stock" to ItemStatus.IN_STOCK,
        "Listed" to ItemStatus.LISTED,
        "Sold" to ItemStatus.SOLD
    )
    androidx.compose.foundation.lazy.LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(options) { (label, status) ->
            FilterChip(
                selected = selected == status,
                onClick = { onSelect(status) },
                label = { Text(label) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}

@Composable
private fun ListedItemCard(
    item: InventoryItem,
    onClick: () -> Unit,
    onEdit: (() -> Unit)? = null,
    onSell: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null
) {
    val haptic = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()
    var showMenu by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .width(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .pointerInput(item.id) {
                detectTapGestures(
                    onTap = {
                        if (showMenu) showMenu = false else onClick()
                    },
                    onLongPress = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        showMenu = true
                    }
                )
            }
    ) {
        // Card content
        Column(
            modifier = Modifier
                .background(SurfaceElevated1, RoundedCornerShape(16.dp))
                .border(1.dp, SurfaceBorder, RoundedCornerShape(16.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(SurfaceElevated2),
                contentAlignment = Alignment.Center
            ) {
                if (item.imageUri != null) {
                    AsyncImage(
                        model = item.imageUri,
                        contentDescription = item.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.Inventory2,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatusChip(status = item.status)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = formatCurrency(item.purchasePrice),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Quick-actions overlay on long press
        if (showMenu) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.35f), RoundedCornerShape(16.dp))
            )

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (onEdit != null) smallActionButton("Edit", MaterialTheme.colorScheme.primary) {
                    showMenu = false; scope.launch { delay(50); onEdit() }
                }
                if (onSell != null) smallActionButton("Sell", Color(0xFF4CAF50)) {
                    showMenu = false; scope.launch { delay(50); onSell() }
                }
                if (onDelete != null) smallActionButton("Delete", MaterialTheme.colorScheme.error) {
                    showMenu = false; scope.launch { delay(50); onDelete() }
                }
            }
        }
    }
}

@Composable
private fun smallActionButton(label: String, color: Color, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = color.copy(alpha = 0.2f),
            contentColor = color
        ),
        modifier = Modifier.size(36.dp)
    ) {
        Icon(
            imageVector = when (label) {
                "Edit" -> Icons.Filled.Edit
                "Sell" -> Icons.Filled.Sell
                "Delete" -> Icons.Filled.Delete
                else -> Icons.Filled.Edit
            },
            contentDescription = label,
            modifier = Modifier.size(16.dp)
        )
    }
}
