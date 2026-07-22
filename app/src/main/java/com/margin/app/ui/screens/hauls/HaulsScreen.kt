package com.margin.app.ui.screens.hauls

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.margin.app.domain.model.Haul
import com.margin.app.ui.components.EmptyState
import com.margin.app.ui.components.MarginPullRefresh
import com.margin.app.ui.theme.FloatingAddButtonPadding
import com.margin.app.ui.components.MarginSearchField
import com.margin.app.ui.components.swipeBack
import com.margin.app.ui.theme.MarginRadius
import com.margin.app.ui.theme.SurfaceBorder
import com.margin.app.ui.theme.SurfaceElevated1
import com.margin.app.ui.theme.TextSecondary
import com.margin.app.ui.util.formatCurrency
import com.margin.app.ui.util.formatDate

@Composable
fun HaulsScreen(
    onHaulClick: (Long) -> Unit,
    onAddHaulClick: () -> Unit,
    onBack: () -> Unit,
    viewModel: HaulsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    var fabPressed by remember { mutableStateOf(false) }

    val fabScale by animateFloatAsState(
        targetValue = if (fabPressed) 0.85f else 1f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 400f),
        label = "fabScale"
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize().swipeBack(onBack),
        contentWindowInsets = WindowInsets(0.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    fabPressed = true
                    onAddHaulClick()
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.scale(fabScale)
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add haul")
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                        Text(
                            text = "Hauls",
                            style = MaterialTheme.typography.displayLarge
                        )
                    }
                }

                item {
                    Text(
                        text = "Trips to the warehouse",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary
                    )
                }

                item {
                    MarginSearchField(
                        value = searchQuery,
                        onValueChange = {
                            searchQuery = it
                            viewModel.onQueryChange(it)
                        },
                        placeholder = "Search hauls"
                    )
                }

                if (uiState.hauls.isEmpty()) {
                    item {
                        EmptyState(
                            icon = Icons.Filled.LocalShipping,
                            title = "No hauls yet",
                            subtitle = "Tap + to record your first warehouse trip."
                        )
                    }
                } else {
                    items(uiState.hauls, key = { it.id }) { haul ->
                        HaulTile(haul = haul, onClick = { onHaulClick(haul.id) })
                    }
                }
            }
        }
    }
}

@Composable
private fun HaulTile(haul: Haul, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceElevated1, MarginRadius.Tile)
            .border(1.dp, SurfaceBorder, MarginRadius.Tile)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = haul.name,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = formatDate(haul.date),
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            if (haul.notes.isNotEmpty()) {
                Text(
                    text = haul.notes,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "${haul.itemCount} items",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = formatCurrency(haul.totalSpent),
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
    }
}
