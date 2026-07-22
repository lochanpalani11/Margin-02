package com.margin.app.data.backup

import com.margin.app.domain.model.InventoryItem
import com.margin.app.domain.model.ItemStatus
import kotlinx.serialization.Serializable

@Serializable
data class BackupItemDto(
    val id: Long,
    val name: String,
    val category: String,
    val purchasePrice: Double,
    val purchaseDate: Long,
    val purchasePlatform: String,
    val status: String,
    val salePrice: Double?,
    val saleDate: Long?,
    val salePlatform: String?,
    val saleFees: Double,
    val notes: String,
    val imageUri: String?
)

@Serializable
data class BackupPayload(
    val schemaVersion: Int = 1,
    val exportedAtMillis: Long,
    val items: List<BackupItemDto>
)

fun InventoryItem.toBackupDto(): BackupItemDto = BackupItemDto(
    id = id,
    name = name,
    category = category,
    purchasePrice = purchasePrice,
    purchaseDate = purchaseDate,
    purchasePlatform = purchasePlatform,
    status = status.name,
    salePrice = salePrice,
    saleDate = saleDate,
    salePlatform = salePlatform,
    saleFees = saleFees,
    notes = notes,
    imageUri = imageUri
)

fun BackupItemDto.toDomain(): InventoryItem = InventoryItem(
    id = id,
    name = name,
    category = category,
    purchasePrice = purchasePrice,
    purchaseDate = purchaseDate,
    purchasePlatform = purchasePlatform,
    status = ItemStatus.valueOf(status),
    salePrice = salePrice,
    saleDate = saleDate,
    salePlatform = salePlatform,
    saleFees = saleFees,
    notes = notes,
    imageUri = imageUri
)
