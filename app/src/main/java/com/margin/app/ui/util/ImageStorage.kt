package com.margin.app.ui.util

import android.content.Context
import android.net.Uri
import java.io.File
import java.util.UUID

object ImageStorage {

    /**
     * Copies the picked image into the app's private files directory so the
     * app retains access to it even if the original content Uri's permission
     * grant expires. Returns the absolute file path to store on the item.
     */
    fun persistPickedImage(context: Context, sourceUri: Uri): String? {
        return try {
            val dir = File(context.filesDir, "item_images").apply { mkdirs() }
            val destination = File(dir, "${UUID.randomUUID()}.jpg")
            context.contentResolver.openInputStream(sourceUri)?.use { input ->
                destination.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            destination.absolutePath
        } catch (e: Exception) {
            null
        }
    }

    fun deleteImage(path: String?) {
        if (path.isNullOrBlank()) return
        try {
            File(path).takeIf { it.exists() }?.delete()
        } catch (_: Exception) {
            // Best-effort cleanup only.
        }
    }
}
