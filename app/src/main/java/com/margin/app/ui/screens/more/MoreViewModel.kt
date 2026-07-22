package com.margin.app.ui.screens.more

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margin.app.domain.repository.ItemRepository
import com.margin.app.ui.util.formatCurrency
import com.margin.app.ui.util.formatDate
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val repository: ItemRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    fun exportCsv() {
        viewModelScope.launch {
            val items = repository.getAllItemsSnapshot()
            val csv = buildCsv(items)
            val file = File(context.cacheDir, "margin_inventory.csv")
            file.writeText(csv)

            val uri: Uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/csv"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_SUBJECT, "Margin Inventory Export")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Export Inventory"))
        }
    }

    private fun buildCsv(items: List<com.margin.app.domain.model.InventoryItem>): String {
        val header = "Name,Category,Purchase Price,Status,Purchase Date,Sale Price,Profit,Days Held,Platform,Notes"
        val rows = items.map { item ->
            val profit = item.profit?.let { formatCurrency(it) } ?: ""
            val salePrice = item.salePrice?.let { formatCurrency(it) } ?: ""
            val name = escapeCsv(item.name)
            val category = escapeCsv(item.category)
            val status = item.status.name
            val purchaseDate = formatDate(item.purchaseDate)
            val platform = escapeCsv(item.purchasePlatform)
            val notes = escapeCsv(item.notes)
            "$name,$category,${formatCurrency(item.purchasePrice)},$status,$purchaseDate,$salePrice,$profit,${item.daysHeld},$platform,$notes"
        }
        return (listOf(header) + rows).joinToString("\n")
    }

    private fun escapeCsv(value: String): String {
        return if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            "\"${value.replace("\"", "\"\"")}\""
        } else {
            value
        }
    }
}
