package com.margin.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.margin.app.data.local.entity.HaulEntity
import kotlinx.coroutines.flow.Flow

data class HaulSummaryRow(
    val id: Long,
    val name: String,
    val date: Long,
    val notes: String,
    val itemCount: Int,
    val totalSpent: Double
)

@Dao
interface HaulDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(haul: HaulEntity): Long

    @Update
    suspend fun update(haul: HaulEntity)

    @Query("DELETE FROM hauls WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM hauls WHERE id = :id")
    suspend fun getById(id: Long): HaulEntity?

    @Query(
        """
        SELECT h.id, h.name, h.date, h.notes,
               COUNT(i.id) AS itemCount,
               COALESCE(SUM(i.purchasePrice), 0.0) AS totalSpent
        FROM hauls h
        LEFT JOIN items i ON i.haulId = h.id
        GROUP BY h.id
        ORDER BY h.date DESC
        """
    )
    fun observeAll(): Flow<List<HaulSummaryRow>>

    @Query("SELECT * FROM hauls ORDER BY date DESC")
    suspend fun getAll(): List<HaulEntity>

    @Query("DELETE FROM hauls")
    suspend fun deleteAll()
}
