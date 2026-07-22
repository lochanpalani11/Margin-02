package com.margin.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.margin.app.data.local.dao.HaulDao
import com.margin.app.data.local.dao.ItemDao
import com.margin.app.data.local.entity.HaulEntity
import com.margin.app.data.local.entity.ItemEntity

@Database(
    entities = [ItemEntity::class, HaulEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MarginDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun haulDao(): HaulDao

    companion object {
        const val DATABASE_NAME = "margin_database"
    }
}
