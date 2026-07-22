package com.margin.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.margin.app.data.local.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

data class DashboardRow(
    val totalItems: Int,
    val inStockCount: Int,
    val listedCount: Int,
    val soldCount: Int,
    val totalInvested: Double,
    val totalRevenue: Double,
    val totalProfit: Double,
    val averageDaysToSell: Double
)

data class ProfitPointRow(
    val periodLabel: String,
    val periodStartMillis: Long,
    val profit: Double
)

data class CategoryBreakdownRow(
    val category: String,
    val totalProfit: Double,
    val itemCount: Int
)

@Dao
interface ItemDao {

    @Query(
        """
        SELECT * FROM items
        WHERE (:status IS NULL OR status = :status)
        AND (:query = '' OR name LIKE '%' || :query || '%' OR category LIKE '%' || :query || '%')
        ORDER BY
            CASE WHEN status = 'SOLD' THEN saleDate ELSE purchaseDate END DESC
        """
    )
    fun observeItems(query: String, status: String?): Flow<List<ItemEntity>>

    @Query("SELECT * FROM items WHERE id = :id")
    fun observeItem(id: Long): Flow<ItemEntity?>

    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getItem(id: Long): ItemEntity?

    @Query("SELECT * FROM items ORDER BY purchaseDate DESC")
    suspend fun getAllItems(): List<ItemEntity>

    @Query("SELECT DISTINCT category FROM items ORDER BY category ASC")
    fun observeCategories(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ItemEntity): Long

    @Update
    suspend fun update(item: ItemEntity)

    @Query("DELETE FROM items WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM items")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ItemEntity>)

    @Query(
        """
        SELECT
            COUNT(*) AS totalItems,
            SUM(CASE WHEN status = 'IN_STOCK' THEN 1 ELSE 0 END) AS inStockCount,
            SUM(CASE WHEN status = 'LISTED' THEN 1 ELSE 0 END) AS listedCount,
            SUM(CASE WHEN status = 'SOLD' THEN 1 ELSE 0 END) AS soldCount,
            COALESCE(SUM(CASE WHEN status != 'SOLD' THEN purchasePrice ELSE 0 END), 0.0) AS totalInvested,
            COALESCE(SUM(CASE WHEN status = 'SOLD' THEN salePrice ELSE 0 END), 0.0) AS totalRevenue,
            COALESCE(SUM(CASE WHEN status = 'SOLD' THEN (salePrice - purchasePrice - saleFees) ELSE 0 END), 0.0) AS totalProfit,
            COALESCE(AVG(CASE WHEN status = 'SOLD' THEN (saleDate - purchaseDate) / 86400000.0 ELSE NULL END), 0.0) AS averageDaysToSell
        FROM items
        """
    )
    fun observeDashboardStats(): Flow<DashboardRow>

    @Query(
        """
        SELECT
            strftime('%Y-%m', saleDate / 1000, 'unixepoch') AS periodLabel,
            CAST(strftime('%s', strftime('%Y-%m-01', saleDate / 1000, 'unixepoch')) AS INTEGER) * 1000 AS periodStartMillis,
            SUM(salePrice - purchasePrice - saleFees) AS profit
        FROM items
        WHERE status = 'SOLD' AND saleDate >= :sinceMillis
        GROUP BY periodLabel
        ORDER BY periodStartMillis ASC
        """
    )
    fun observeProfitOverTime(sinceMillis: Long): Flow<List<ProfitPointRow>>

    @Query(
        """
        SELECT
            strftime('%Y-%m-%d', saleDate / 1000, 'unixepoch') AS periodLabel,
            CAST(strftime('%s', strftime('%Y-%m-%d', saleDate / 1000, 'unixepoch')) AS INTEGER) * 1000 AS periodStartMillis,
            SUM(salePrice - purchasePrice - saleFees) AS profit
        FROM items
        WHERE status = 'SOLD' AND saleDate >= :sinceMillis
        GROUP BY periodLabel
        ORDER BY periodStartMillis ASC
        """
    )
    fun observeProfitByDay(sinceMillis: Long): Flow<List<ProfitPointRow>>

    @Query(
        """
        SELECT
            strftime('%Y-%W', saleDate / 1000, 'unixepoch') AS periodLabel,
            CAST(strftime('%s', strftime('%Y-%m-%d', saleDate / 1000, 'unixepoch', 'weekday 1')) AS INTEGER) * 1000 AS periodStartMillis,
            SUM(salePrice - purchasePrice - saleFees) AS profit
        FROM items
        WHERE status = 'SOLD' AND saleDate >= :sinceMillis
        GROUP BY periodLabel
        ORDER BY periodStartMillis ASC
        """
    )
    fun observeProfitByWeek(sinceMillis: Long): Flow<List<ProfitPointRow>>

    @Query(
        """
        SELECT
            category,
            SUM(salePrice - purchasePrice - saleFees) AS totalProfit,
            COUNT(*) AS itemCount
        FROM items
        WHERE status = 'SOLD'
        GROUP BY category
        ORDER BY totalProfit DESC
        """
    )
    fun observeCategoryBreakdown(): Flow<List<CategoryBreakdownRow>>
}
