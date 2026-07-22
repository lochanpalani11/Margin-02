package com.margin.app.ui.screens.bidcalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.margin.app.ui.components.MarginTextField
import com.margin.app.ui.components.swipeBack
import com.margin.app.ui.theme.FloatingAddButtonPadding
import com.margin.app.ui.theme.MarginRadius
import com.margin.app.ui.theme.MarginTheme
import com.margin.app.ui.theme.SurfaceBorder
import com.margin.app.ui.theme.SurfaceElevated1
import com.margin.app.ui.theme.TextSecondary
import com.margin.app.ui.util.formatCurrency

@Composable
fun BidCalculatorScreen(
    onBack: () -> Unit,
    viewModel: BidCalculatorViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    BidCalculatorContent(
        uiState = uiState,
        onBidAmountChange = viewModel::onBidAmountChange,
        onBack = onBack
    )
}

@Composable
internal fun BidCalculatorContent(
    uiState: BidCalculatorUiState,
    onBidAmountChange: (String) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize().swipeBack(onBack),
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            TopAppBar(
                title = { Text("Bid Calculator") },
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
                start = 20.dp,
                top = 24.dp,
                end = 20.dp,
                bottom = FloatingAddButtonPadding
            ),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Big bold header
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "MacBid",
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 42.sp
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Cost Calculator",
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 42.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Enter your winning bid to see the true cost",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Big input field
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Winning Bid",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    MarginTextField(
                        label = "Amount",
                        value = uiState.bidAmount,
                        onValueChange = onBidAmountChange,
                        keyboardType = KeyboardType.Decimal,
                        placeholder = "0.00"
                    )
                }
            }

            // Big cost breakdown card
            item {
                val bid = uiState.bidAmount.toDoubleOrNull() ?: 0.0
                val lotFee = if (bid > 0) 3.0 else 0.0
                val buyerPremium = bid * 0.15
                val subtotal = bid + lotFee + buyerPremium
                val salesTax = subtotal * 0.07
                val total = subtotal + salesTax

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceElevated1, MarginRadius.Card)
                        .padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Cost Breakdown",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    BigBreakdownRow("Winning Bid", bid)
                    BigBreakdownRow("Lot Fee", lotFee)
                    BigBreakdownRow("Buyer's Premium (15%)", buyerPremium)

                    HorizontalDivider(
                        color = SurfaceBorder,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )

                    BigBreakdownRow("Subtotal", subtotal, bold = true)
                    BigBreakdownRow("Sales Tax (7%)", salesTax)

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Big total
                    Text(
                        text = "TOTAL COST",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextSecondary,
                        letterSpacing = 2.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = formatCurrency(total),
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Footnote
            item {
                Text(
                    text = "Lot fee: \$3.00 flat  ·  Buyer's premium: 15% of winning bid  ·  Sales tax: 7% on subtotal",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun BigBreakdownRow(
    label: String,
    amount: Double,
    bold: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = if (bold) MaterialTheme.colorScheme.onSurface else TextSecondary
        )
        Text(
            text = formatCurrency(amount),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal
            ),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true, device = "id:pixel_7")
@Composable
private fun BidCalculatorContentPreview() {
    MarginTheme {
        BidCalculatorContent(
            uiState = BidCalculatorUiState(bidAmount = "150.00"),
            onBidAmountChange = {},
            onBack = {}
        )
    }
}
