package com.margin.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.margin.app.domain.model.InventoryItem
import com.margin.app.domain.model.ItemStatus
import com.margin.app.ui.theme.MarginRadius
import com.margin.app.ui.theme.SurfaceBorder
import com.margin.app.ui.theme.SurfaceElevated1
import com.margin.app.ui.theme.SurfaceElevated2
import com.margin.app.ui.theme.NeutralAmber
import com.margin.app.ui.theme.ProfitGreen
import com.margin.app.ui.theme.TextSecondary
import com.margin.app.ui.util.formatCurrency
import com.margin.app.ui.util.formatDays
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InventoryTile(
    item: InventoryItem,
    onClick: () -> Unit,
    onEdit: (() -> Unit)? = null,
    onSell: (() -> Unit)? = null,
    onQuickSell: (() -> Unit)? = null,
    onMarkListed: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()
    var showMenu by remember { mutableStateOf(false) }

    // Staggered entrance delays via different spring stiffnesses
    val editScale by animateFloatAsState(
        targetValue = if (showMenu) 1f else 0f,
        animationSpec = if (showMenu)
            spring(dampingRatio = 0.5f, stiffness = 400f)
        else
            tween(100),
        label = "editScale"
    )
    val sellScale by animateFloatAsState(
        targetValue = if (showMenu) 1f else 0f,
        animationSpec = if (showMenu)
            spring(dampingRatio = 0.4f, stiffness = 350f)
        else
            tween(80),
        label = "sellScale"
    )
    val deleteScale by animateFloatAsState(
        targetValue = if (showMenu) 1f else 0f,
        animationSpec = if (showMenu)
            spring(dampingRatio = 0.35f, stiffness = 300f)
        else
            tween(60),
        label = "deleteScale"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(MarginRadius.Tile)
            .pointerInput(item.id) {
                detectTapGestures(
                    onTap = {
                        if (showMenu) {
                            showMenu = false
                        } else {
                            onClick()
                        }
                    },
                    onLongPress = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        showMenu = true
                    }
                )
            }
    ) {
        // Normal tile content
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceElevated1, MarginRadius.Tile)
                .border(1.dp, SurfaceBorder, MarginRadius.Tile)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceElevated2),
                contentAlignment = Alignment.Center
            ) {
                if (item.imageUri != null) {
                    AsyncImage(
                        model = item.imageUri,
                        contentDescription = item.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(14.dp))
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.Inventory2,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatusChip(status = item.status)
                    Text(
                        text = item.category,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(horizontalAlignment = Alignment.End) {
                if (item.status == ItemStatus.SOLD && item.profit != null) {
                    ProfitIndicator(
                        amount = item.profit!!,
                        style = MaterialTheme.typography.titleMedium,
                        showIcon = false
                    )
                } else {
                    Text(
                        text = formatCurrency(item.purchasePrice),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Text(
                    text = formatDays(item.daysHeld),
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }

        // Quick-actions overlay — appears on long press
        if (showMenu) {
            // Semi-transparent scrim
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.35f), MarginRadius.Tile)
            )

            // Action buttons — centered
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Edit button
                if (onEdit != null) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.scale(editScale)
                    ) {
                        IconButton(
                            onClick = {
                                showMenu = false
                                scope.launch {
                                    delay(50)
                                    onEdit()
                                }
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Edit",
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Text(
                            text = "Edit",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                // Sell button (only for non-sold items)
                if (onSell != null && item.status != ItemStatus.SOLD) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.scale(sellScale)
                    ) {
                        IconButton(
                            onClick = {
                                showMenu = false
                                scope.launch {
                                    delay(50)
                                    onSell()
                                }
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = ProfitGreen.copy(alpha = 0.2f),
                                contentColor = ProfitGreen
                            ),
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Sell,
                                contentDescription = "Sell",
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Text(
                            text = "Sell",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                // Quick Sell button (only for non-sold items)
                if (onQuickSell != null && item.status != ItemStatus.SOLD) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.scale(sellScale)
                    ) {
                        IconButton(
                            onClick = {
                                showMenu = false
                                scope.launch {
                                    delay(50)
                                    onQuickSell()
                                }
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = ProfitGreen.copy(alpha = 0.2f),
                                contentColor = ProfitGreen
                            ),
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Sell,
                                contentDescription = "Quick Sell",
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Text(
                            text = "Quick",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                // Mark as Listed button (only for In Stock items)
                if (onMarkListed != null && item.status == ItemStatus.IN_STOCK) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.scale(sellScale)
                    ) {
                        IconButton(
                            onClick = {
                                showMenu = false
                                scope.launch {
                                    delay(50)
                                    onMarkListed()
                                }
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = NeutralAmber.copy(alpha = 0.2f),
                                contentColor = NeutralAmber
                            ),
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.List,
                                contentDescription = "Mark Listed",
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Text(
                            text = "List",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                // Delete button
                if (onDelete != null) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.scale(deleteScale)
                    ) {
                        IconButton(
                            onClick = {
                                showMenu = false
                                scope.launch {
                                    delay(50)
                                    onDelete()
                                }
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.2f),
                                contentColor = MaterialTheme.colorScheme.error
                            ),
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete",
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Text(
                            text = "Delete",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}
