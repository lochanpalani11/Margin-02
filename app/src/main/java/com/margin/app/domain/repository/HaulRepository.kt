package com.margin.app.domain.repository

import com.margin.app.domain.model.Haul
import kotlinx.coroutines.flow.Flow

interface HaulRepository {
    fun observeAll(): Flow<List<Haul>>
    suspend fun getById(id: Long): Haul?
    suspend fun createHaul(name: String, date: Long, notes: String): Long
    suspend fun updateHaul(haul: Haul)
    suspend fun deleteHaul(id: Long)
}
