package com.margin.app.ui.screens.additem

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.margin.app.domain.model.Haul
import com.margin.app.ui.components.MarginPrimaryButton
import com.margin.app.ui.components.MarginTextField
import com.margin.app.ui.components.swipeBack
import com.margin.app.ui.theme.MarginRadius
import com.margin.app.ui.theme.SurfaceBorder
import com.margin.app.ui.theme.SurfaceElevated1
import com.margin.app.ui.theme.SurfaceElevated2
import com.margin.app.ui.theme.TextSecondary
import com.margin.app.ui.util.ImageStorage
import com.margin.app.ui.util.formatDate

@Composable
fun AddEditItemScreen(
    onSaved: () -> Unit,
    onBack: () -> Unit,
    viewModel: AddEditItemViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val categorySuggestions by viewModel.categorySuggestions.collectAsState()
    val context = LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) onSaved()
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val path = ImageStorage.persistPickedImage(context, uri)
            viewModel.onImagePicked(path)
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = uiState.purchaseDate)
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

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize().swipeBack(onBack),
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            TopAppBar(
                title = { Text(if (uiState.isEditing) "Edit Item" else "Add Item") },
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
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                horizontal = 20.dp,
                vertical = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Haul selector — always visible when not editing
            if (!uiState.isEditing) {
                item {
                    HaulSelector(
                        selectedHaulId = uiState.selectedHaulId,
                        availableHauls = uiState.availableHauls,
                        onHaulSelected = viewModel::onHaulSelected
                    )
                }
            }

            // Haul banner — shown only when arriving from a haul detail screen
            if (uiState.showHaulBanner && uiState.haulName != null) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SurfaceElevated1, MarginRadius.Card)
                            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), MarginRadius.Card)
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocalShipping,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Adding to haul",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSecondary
                            )
                            Text(
                                text = uiState.haulName!!,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(SurfaceElevated2)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { imagePicker.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.imageUri != null) {
                        AsyncImage(
                            model = uiState.imageUri,
                            contentDescription = "Item photo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(96.dp)
                                .clip(RoundedCornerShape(20.dp))
                        )
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Filled.AddAPhoto,
                                contentDescription = null,
                                tint = TextSecondary
                            )
                            Text(
                                text = "Photo",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSecondary,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }
            }

            item {
                MarginTextField(
                    label = "Item name",
                    value = uiState.name,
                    onValueChange = viewModel::onNameChange,
                    errorText = if (uiState.name.isNotEmpty() || uiState.isEditing) uiState.nameError else null,
                    placeholder = "e.g. Nike Dunk Low Panda"
                )
            }

            item {
                Column {
                    MarginTextField(
                        label = "Category",
                        value = uiState.category,
                        onValueChange = viewModel::onCategoryChange,
                        placeholder = "e.g. Sneakers"
                    )
                    if (categorySuggestions.isNotEmpty()) {
                        Row(
                            modifier = Modifier.padding(top = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            categorySuggestions.take(4).forEach { suggestion ->
                                CategorySuggestionChip(
                                    label = suggestion,
                                    onClick = { viewModel.onCategoryChange(suggestion) }
                                )
                            }
                        }
                    }
                }
            }

            item {
                MarginTextField(
                    label = "Purchase price",
                    value = uiState.purchasePrice,
                    onValueChange = viewModel::onPriceChange,
                    errorText = if (uiState.purchasePrice.isNotEmpty()) uiState.priceError else null,
                    keyboardType = KeyboardType.Decimal,
                    placeholder = "0.00"
                )
            }

            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    MarginTextField(
                        label = "Purchase date",
                        value = formatDate(uiState.purchaseDate),
                        onValueChange = {},
                        readOnly = true
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clip(MaterialTheme.shapes.small)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { showDatePicker = true }
                    )
                    Icon(
                        imageVector = Icons.Filled.CalendarToday,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 16.dp)
                            .size(20.dp)
                    )
                }
            }

            item {
                MarginTextField(
                    label = "Purchased from",
                    value = uiState.purchasePlatform,
                    onValueChange = viewModel::onPlatformChange,
                    placeholder = "e.g. StockX, thrift store, garage sale"
                )
            }

            item {
                MarginTextField(
                    label = "Notes",
                    value = uiState.notes,
                    onValueChange = viewModel::onNotesChange,
                    singleLine = false,
                    minLines = 3,
                    placeholder = "Condition, size, anything worth remembering"
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
                MarginPrimaryButton(
                    text = if (uiState.isEditing) "Save Changes" else "Add to Inventory",
                    onClick = viewModel::save,
                    enabled = uiState.isValid
                )
            }
        }
    }
}

@Composable
private fun CategorySuggestionChip(label: String, onClick: () -> Unit) {
    Text(
        text = label,
        style = MaterialTheme.typography.labelMedium,
        color = TextSecondary,
        modifier = Modifier
            .clip(RoundedCornerShape(100))
            .background(SurfaceElevated2)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}

@Composable
private fun HaulSelector(
    selectedHaulId: Long?,
    availableHauls: List<Haul>,
    onHaulSelected: (Long?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedHaul = availableHauls.find { it.id == selectedHaulId }
    val displayText = selectedHaul?.name ?: "None (standalone item)"

    Column {
        Text(
            text = "Link to haul",
            style = MaterialTheme.typography.labelMedium,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceElevated2, RoundedCornerShape(12.dp))
                    .border(1.dp, SurfaceBorder, RoundedCornerShape(12.dp))
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { expanded = true }
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    if (selectedHaul != null) {
                        Icon(
                            imageVector = Icons.Filled.LocalShipping,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        text = displayText,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (selectedHaul != null) MaterialTheme.colorScheme.onSurface else TextSecondary
                    )
                }
                Text(text = "▾", color = TextSecondary)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.85f)
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            "None (standalone item)",
                            color = if (selectedHaulId == null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    },
                    onClick = {
                        onHaulSelected(null)
                        expanded = false
                    }
                )
                if (availableHauls.isNotEmpty()) {
                    availableHauls.forEach { haul ->
                        val isSelected = haul.id == selectedHaulId
                        DropdownMenuItem(
                            text = {
                                Text(
                                    haul.name,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )
                            },
                            onClick = {
                                onHaulSelected(haul.id)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}
