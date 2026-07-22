package com.margin.app.domain.model

data class DashboardStats(
    val totalItems: Int = 0,
    val inStockCount: Int = 0,
    val listedCount: Int = 0,
    val soldCount: Int = 0,
    val totalInvested: Double = 0.0,   // cost of everything currently held (in stock + listed)
    val totalRevenue: Double = 0.0,    // gross sale price of everything sold
    val totalProfit: Double = 0.0,     // net profit of everything sold
    val averageDaysToSell: Double = 0.0
) {
    val overallRoiPercent: Double
        get() {
            val soldCost = if (totalRevenue > 0 && totalProfit != 0.0) totalRevenue - totalProfit else 0.0
            return if (soldCost > 0.0) (totalProfit / soldCost) * 100 else 0.0
        }
}

/** A single point on the profit-over-time chart. */
data class ProfitPoint(
    val periodLabel: String,
    val periodStartMillis: Long,
    val profit: Double
)

data class CategoryBreakdown(
    val category: String,
    val totalProfit: Double,
    val itemCount: Int
)
