package com.trubitsyna.homework.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.util.UUID
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
) : ImageRepository {

    override suspend fun saveImage(uri: Uri): Uri? {
        var newUri: Uri? = null
        try {
            val imageFileInternalStorage = File(context.filesDir, UUID.randomUUID().toString())
            if (!imageFileInternalStorage.exists()) {
                context.contentResolver.openInputStream(uri).use { input ->
                    imageFileInternalStorage.outputStream().use { output ->
                        input?.copyTo(output)
                    }
                }
                newUri = imageFileInternalStorage.toUri()
            }
        } catch (e: Exception) {
            Log.e("FileOperations", "Something wrong with files.")
        }
        return newUri
    }

    override suspend fun deleteImage(uri: Uri) {
        val imageFileInternalStorage = uri.toFile()
        val isDeleted = imageFileInternalStorage.delete()
        if (isDeleted) {
            Log.i("FileOperations", "File deleted!")
        } else {
            Log.e("FileOperations", "File cannot be deleted!")
        }
    }
}