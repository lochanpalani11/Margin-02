package com.margin.app.ui.util

import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.abs

private val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
private val dateFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy")

fun formatCurrency(amount: Double): String = currencyFormat.format(amount)

fun formatSignedCurrency(amount: Double): String {
    val formatted = currencyFormat.format(abs(amount))
    return if (amount < 0) "-$formatted" else "+$formatted"
}

fun formatPercent(value: Double, decimals: Int = 1): String =
    "${if (value >= 0) "+" else ""}${"%.${decimals}f".format(value)}%"

fun formatDate(epochMillis: Long): String =
    dateFormatter.format(Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault()))

fun formatDays(days: Long): String = if (days == 1L) "1 day" else "$days days"
