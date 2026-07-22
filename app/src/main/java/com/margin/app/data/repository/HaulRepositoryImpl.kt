package com.margin.app.data.repository

import com.margin.app.data.local.dao.HaulDao
import com.margin.app.data.local.dao.HaulSummaryRow
import com.margin.app.data.local.entity.HaulEntity
import com.margin.app.domain.model.Haul
import com.margin.app.domain.repository.HaulRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HaulRepositoryImpl @Inject constructor(
    private val dao: HaulDao
) : HaulRepository {

    override fun observeAll(): Flow<List<Haul>> =
        dao.observeAll().map { rows -> rows.map { it.toDomain() } }

    override suspend fun getById(id: Long): Haul? =
        dao.getById(id)?.let { entity ->
            Haul(id = entity.id, name = entity.name, date = entity.date, notes = entity.notes)
        }

    override suspend fun createHaul(name: String, date: Long, notes: String): Long =
        dao.insert(HaulEntity(name = name, date = date, notes = notes))

    override suspend fun updateHaul(haul: Haul) {
        dao.update(HaulEntity(id = haul.id, name = haul.name, date = haul.date, notes = haul.notes))
    }

    override suspend fun deleteHaul(id: Long) = dao.deleteById(id)
}

private fun HaulSummaryRow.toDomain(): Haul = Haul(
    id = id,
    name = name,
    date = date,
    notes = notes,
    itemCount = itemCount,
    totalSpent = totalSpent
)
