package com.margin.app.ui.screens.sellitem

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.margin.app.ui.components.ConfettiOverlay
import com.margin.app.ui.components.MarginPrimaryButton
import com.margin.app.ui.components.MarginTextField
import com.margin.app.ui.components.ProfitIndicator
import com.margin.app.ui.components.swipeBack
import com.margin.app.ui.theme.MarginRadius
import com.margin.app.ui.theme.SurfaceElevated1
import com.margin.app.ui.theme.TextSecondary
import com.margin.app.ui.util.formatCurrency
import com.margin.app.ui.util.formatDate
import kotlinx.coroutines.delay

@Composable
fun SellItemScreen(
    onSaved: () -> Unit,
    onBack: () -> Unit,
    viewModel: SellItemViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }
    var showConfetti by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            showConfetti = true
            delay(2000)
            onSaved()
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = uiState.saleDate)
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { viewModel.onDateChange(it) }
                    showDatePicker = false
                }) { Text("Done") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize().swipeBack(onBack),
        contentWindowInsets = WindowInsets(0.dp),
            topBar = {
                TopAppBar(
                    title = { Text("Mark as Sold") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                )
            }
        ) { padding ->
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
                uiState.item?.let { item ->
                    item {
                        Column {
                            Text(text = item.name, style = MaterialTheme.typography.headlineMedium)
                            Text(
                                text = "Bought for ${formatCurrency(item.purchasePrice)}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }

                item {
                    MarginTextField(
                        label = "Sale price",
                        value = uiState.salePrice,
                        onValueChange = viewModel::onPriceChange,
                        errorText = if (uiState.salePrice.isNotEmpty()) uiState.priceError else null,
                        keyboardType = KeyboardType.Decimal,
                        placeholder = "0.00"
                    )
                }

                item {
                    MarginTextField(
                        label = "Fees (platform, shipping, etc.)",
                        value = uiState.saleFees,
                        onValueChange = viewModel::onFeesChange,
                        errorText = if (uiState.saleFees.isNotEmpty()) uiState.feesError else null,
                        keyboardType = KeyboardType.Decimal,
                        placeholder = "0.00"
                    )
                }

                item {
                    MarginTextField(
                        label = "Sold on",
                        value = uiState.salePlatform,
                        onValueChange = viewModel::onPlatformChange,
                        placeholder = "e.g. eBay, StockX, in person"
                    )
                }

                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        MarginTextField(
                            label = "Sale date",
                            value = formatDate(uiState.saleDate),
                            onValueChange = {},
                            readOnly = true
                        )
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { showDatePicker = true }
                        )
                    }
                }

                uiState.projectedProfit?.let { profit ->
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(SurfaceElevated1, MarginRadius.Card)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Projected profit",
                                style = MaterialTheme.typography.labelMedium,
                                color = TextSecondary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            ProfitIndicator(amount = profit, style = MaterialTheme.typography.headlineMedium)
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    MarginPrimaryButton(
                        text = "Confirm Sale",
                        onClick = viewModel::confirmSale,
                        enabled = uiState.isValid
                    )
                }
            }
        }

        if (showConfetti) {
            ConfettiOverlay()
        }
    }
}
