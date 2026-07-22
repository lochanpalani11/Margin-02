package com.margin.app.data.local

import androidx.room.TypeConverter
import com.margin.app.domain.model.ItemStatus

class Converters {
    @TypeConverter
    fun fromStatus(status: ItemStatus): String = status.name

    @TypeConverter
    fun toStatus(value: String): ItemStatus = ItemStatus.valueOf(value)
}
