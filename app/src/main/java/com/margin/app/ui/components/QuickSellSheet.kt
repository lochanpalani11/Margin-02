package com.margin.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickSellSheet(
    itemName: String,
    purchasePrice: Double,
    onConfirm: (salePrice: Double, fees: Double) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var salePrice by remember { mutableStateOf("") }
    var fees by remember { mutableStateOf("0") }

    val parsedPrice = salePrice.toDoubleOrNull()
    val parsedFees = fees.toDoubleOrNull() ?: 0.0
    val projectedProfit = parsedPrice?.let { it - purchasePrice - parsedFees }
    val isValid = parsedPrice != null && parsedPrice > 0

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(itemName, style = MaterialTheme.typography.titleLarge)
            Text(
                text = "Bought for ${com.margin.app.ui.util.formatCurrency(purchasePrice)}",
                style = MaterialTheme.typography.bodyMedium,
                color = com.margin.app.ui.theme.TextSecondary
            )

            MarginTextField(
                label = "Sale price",
                value = salePrice,
                onValueChange = { salePrice = it },
                keyboardType = KeyboardType.Decimal,
                placeholder = "0.00"
            )

            MarginTextField(
                label = "Fees (optional)",
                value = fees,
                onValueChange = { fees = it },
                keyboardType = KeyboardType.Decimal,
                placeholder = "0.00"
            )

            if (projectedProfit != null) {
                Text(
                    text = "Profit: ${com.margin.app.ui.util.formatSignedCurrency(projectedProfit)}",
                    style = MaterialTheme.typography.titleMedium,
                    color = when {
                        projectedProfit > 0 -> com.margin.app.ui.theme.ProfitGreen
                        projectedProfit < 0 -> com.margin.app.ui.theme.LossRed
                        else -> com.margin.app.ui.theme.NeutralAmber
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            MarginPrimaryButton(
                text = "Confirm Quick Sale",
                onClick = {
                    if (isValid) {
                        onConfirm(parsedPrice!!, parsedFees)
                        onDismiss()
                    }
                },
                enabled = isValid
            )
        }
    }
}
