package com.margin.app.data.repository

import com.margin.app.data.local.dao.ItemDao
import com.margin.app.data.local.entity.toDomain
import com.margin.app.data.local.entity.toEntity
import com.margin.app.domain.model.CategoryBreakdown
import com.margin.app.domain.model.DashboardStats
import com.margin.app.domain.model.InventoryItem
import com.margin.app.domain.model.ItemStatus
import com.margin.app.domain.model.ProfitPoint
import com.margin.app.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepositoryImpl @Inject constructor(
    private val dao: ItemDao
) : ItemRepository {

    override fun observeItems(query: String, statusFilter: ItemStatus?): Flow<List<InventoryItem>> =
        dao.observeItems(query.trim(), statusFilter?.name).map { list -> list.map { it.toDomain() } }

    override fun observeItem(id: Long): Flow<InventoryItem?> =
        dao.observeItem(id).map { it?.toDomain() }

    override fun observeDashboardStats(): Flow<DashboardStats> =
        dao.observeDashboardStats().map { row ->
            DashboardStats(
                totalItems = row.totalItems,
                inStockCount = row.inStockCount,
                listedCount = row.listedCount,
                soldCount = row.soldCount,
                totalInvested = row.totalInvested,
                totalRevenue = row.totalRevenue,
                totalProfit = row.totalProfit,
                averageDaysToSell = row.averageDaysToSell
            )
        }

    override fun observeProfitOverTime(monthsBack: Int): Flow<List<ProfitPoint>> {
        val since = YearMonth.now().minusMonths(monthsBack.toLong() - 1)
            .atDay(1)
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        return dao.observeProfitOverTime(since).map { rows ->
            rows.map { row ->
                val ym = YearMonth.parse(row.periodLabel)
                val label = ym.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                ProfitPoint(
                    periodLabel = label,
                    periodStartMillis = row.periodStartMillis,
                    profit = row.profit
                )
            }
        }
    }

    override fun observeProfitByDay(daysBack: Int): Flow<List<ProfitPoint>> {
        val since = LocalDate.now().minusDays(daysBack.toLong() - 1)
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        return dao.observeProfitByDay(since).map { rows ->
            rows.map { row ->
                val date = LocalDate.parse(row.periodLabel, DateTimeFormatter.ISO_LOCAL_DATE)
                val label = date.format(DateTimeFormatter.ofPattern("M/d"))
                ProfitPoint(
                    periodLabel = label,
                    periodStartMillis = row.periodStartMillis,
                    profit = row.profit
                )
            }
        }
    }

    override fun observeProfitByWeek(weeksBack: Int): Flow<List<ProfitPoint>> {
        val since = LocalDate.now().minusWeeks(weeksBack.toLong())
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        return dao.observeProfitByWeek(since).map { rows ->
            rows.map { row ->
                // periodLabel is 'YYYY-WW', parse to get the Monday of that week
                val parts = row.periodLabel.split("-")
                val year = parts[0].toInt()
                val week = parts[1].toInt()
                val weekFields = WeekFields.ISO
                val firstDayOfYear = LocalDate.of(year, 1, 1)
                val monday = firstDayOfYear.with(weekFields.weekOfYear(), week.toLong())
                    .with(weekFields.dayOfWeek(), 1)
                val label = monday.format(DateTimeFormatter.ofPattern("M/d"))
                ProfitPoint(
                    periodLabel = label,
                    periodStartMillis = row.periodStartMillis,
                    profit = row.profit
                )
            }
        }
    }

    override fun observeCategoryBreakdown(): Flow<List<CategoryBreakdown>> =
        dao.observeCategoryBreakdown().map { rows ->
            rows.map { CategoryBreakdown(it.category, it.totalProfit, it.itemCount) }
        }

    override fun observeCategories(): Flow<List<String>> = dao.observeCategories()

    override suspend fun getItem(id: Long): InventoryItem? = dao.getItem(id)?.toDomain()

    override suspend fun addItem(item: InventoryItem): Long = dao.insert(item.toEntity())

    override suspend fun updateItem(item: InventoryItem) = dao.update(item.toEntity())

    override suspend fun deleteItem(id: Long) = dao.deleteById(id)

    override suspend fun markAsListed(id: Long) {
        val existing = dao.getItem(id) ?: return
        dao.update(existing.copy(status = ItemStatus.LISTED))
    }

    override suspend fun markAsSold(
        id: Long,
        salePrice: Double,
        saleDate: Long,
        salePlatform: String,
        saleFees: Double
    ) {
        val existing = dao.getItem(id) ?: return
        dao.update(
            existing.copy(
                status = ItemStatus.SOLD,
                salePrice = salePrice,
                saleDate = saleDate,
                salePlatform = salePlatform,
                saleFees = saleFees
            )
        )
    }

    override suspend fun getAllItemsSnapshot(): List<InventoryItem> =
        dao.getAllItems().map { it.toDomain() }

    override suspend fun replaceAllItems(items: List<InventoryItem>) {
        dao.deleteAll()
        dao.insertAll(items.map { it.toEntity() })
    }
}
