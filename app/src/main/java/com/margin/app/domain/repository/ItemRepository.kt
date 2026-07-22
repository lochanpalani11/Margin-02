package com.margin.app.domain.repository

import com.margin.app.domain.model.CategoryBreakdown
import com.margin.app.domain.model.DashboardStats
import com.margin.app.domain.model.InventoryItem
import com.margin.app.domain.model.ItemStatus
import com.margin.app.domain.model.ProfitPoint
import kotlinx.coroutines.flow.Flow

interface ItemRepository {

    fun observeItems(query: String, statusFilter: ItemStatus?): Flow<List<InventoryItem>>

    fun observeItem(id: Long): Flow<InventoryItem?>

    fun observeDashboardStats(): Flow<DashboardStats>

    fun observeProfitOverTime(monthsBack: Int): Flow<List<ProfitPoint>>

    fun observeProfitByDay(daysBack: Int): Flow<List<ProfitPoint>>

    fun observeProfitByWeek(weeksBack: Int): Flow<List<ProfitPoint>>

    fun observeCategoryBreakdown(): Flow<List<CategoryBreakdown>>

    fun observeCategories(): Flow<List<String>>

    suspend fun getItem(id: Long): InventoryItem?

    suspend fun addItem(item: InventoryItem): Long

    suspend fun updateItem(item: InventoryItem)

    suspend fun deleteItem(id: Long)

    suspend fun markAsListed(id: Long)

    suspend fun markAsSold(id: Long, salePrice: Double, saleDate: Long, salePlatform: String, saleFees: Double)

    suspend fun getAllItemsSnapshot(): List<InventoryItem>

    suspend fun replaceAllItems(items: List<InventoryItem>)
}
