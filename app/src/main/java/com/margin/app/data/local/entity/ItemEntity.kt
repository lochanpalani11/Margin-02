package com.margin.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.margin.app.domain.model.InventoryItem
import com.margin.app.domain.model.ItemStatus

@Entity(
    tableName = "items",
    foreignKeys = [
        ForeignKey(
            entity = HaulEntity::class,
            parentColumns = ["id"],
            childColumns = ["haulId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("haulId")]
)
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val category: String,
    val purchasePrice: Double,
    val purchaseDate: Long,
    val purchasePlatform: String,
    val status: ItemStatus,
    val salePrice: Double?,
    val saleDate: Long?,
    val salePlatform: String?,
    val saleFees: Double,
    val notes: String,
    val imageUri: String?,
    val haulId: Long? = null
)

fun ItemEntity.toDomain(): InventoryItem = InventoryItem(
    id = id,
    name = name,
    category = category,
    purchasePrice = purchasePrice,
    purchaseDate = purchaseDate,
    purchasePlatform = purchasePlatform,
    status = status,
    salePrice = salePrice,
    saleDate = saleDate,
    salePlatform = salePlatform,
    saleFees = saleFees,
    notes = notes,
    imageUri = imageUri,
    haulId = haulId
)

fun InventoryItem.toEntity(): ItemEntity = ItemEntity(
    id = id,
    name = name,
    category = category,
    purchasePrice = purchasePrice,
    purchaseDate = purchaseDate,
    purchasePlatform = purchasePlatform,
    status = status,
    salePrice = salePrice,
    saleDate = saleDate,
    salePlatform = salePlatform,
    saleFees = saleFees,
    notes = notes,
    imageUri = imageUri,
    haulId = haulId
)
