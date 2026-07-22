package com.margin.app.data.backup

import android.content.Context
import android.net.Uri
import com.margin.app.domain.model.InventoryItem
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Handles fully local export/import of the user's data as a JSON file.
 * Everything stays on-device — the user picks the destination/source file
 * via the system file picker (Storage Access Framework), no account or
 * network connection required.
 */
@Singleton
class BackupManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    suspend fun exportToUri(uri: Uri, items: List<InventoryItem>) = withContext(Dispatchers.IO) {
        val payload = BackupPayload(
            exportedAtMillis = System.currentTimeMillis(),
            items = items.map { it.toBackupDto() }
        )
        val content = json.encodeToString(payload)
        context.contentResolver.openOutputStream(uri)?.use { stream ->
            stream.write(content.toByteArray(Charsets.UTF_8))
        } ?: error("Unable to open destination file")
    }

    suspend fun importFromUri(uri: Uri): List<InventoryItem> = withContext(Dispatchers.IO) {
        val content = context.contentResolver.openInputStream(uri)?.use { stream ->
            stream.readBytes().toString(Charsets.UTF_8)
        } ?: error("Unable to open selected file")
        val payload = json.decodeFromString<BackupPayload>(content)
        payload.items.map { it.toDomain() }
    }

    fun defaultExportFileName(): String {
        val timestamp = System.currentTimeMillis()
        return "margin-backup-$timestamp.json"
    }
}
