package com.margin.app.domain.model

import java.util.concurrent.TimeUnit

/**
 * A single flip: one item bought with the intent to resell.
 * All money is stored as a Double representing whole currency units (e.g. dollars).
 */
data class InventoryItem(
    val id: Long = 0,
    val name: String,
    val category: String,
    val purchasePrice: Double,
    val purchaseDate: Long,
    val purchasePlatform: String,
    val status: ItemStatus,
    val salePrice: Double? = null,
    val saleDate: Long? = null,
    val salePlatform: String? = null,
    val saleFees: Double = 0.0,
    val notes: String = "",
    val imageUri: String? = null,
    val haulId: Long? = null
) {
    /** Net profit after fees. Null until the item is sold. */
    val profit: Double?
        get() = if (status == ItemStatus.SOLD && salePrice != null) {
            salePrice - purchasePrice - saleFees
        } else null

    /** Profit as a percentage of the sale price. */
    val marginPercent: Double?
        get() = profit?.let { p ->
            if (salePrice != null && salePrice > 0.0) (p / salePrice) * 100 else null
        }

    /** Return on the original investment. */
    val roiPercent: Double?
        get() = profit?.let { p ->
            if (purchasePrice > 0.0) (p / purchasePrice) * 100 else null
        }

    /** Days between purchase and sale (or purchase and now, if unsold). */
    val daysHeld: Long
        get() {
            val end = saleDate ?: System.currentTimeMillis()
            val diff = end - purchaseDate
            return TimeUnit.MILLISECONDS.toDays(diff).coerceAtLeast(0)
        }
}
