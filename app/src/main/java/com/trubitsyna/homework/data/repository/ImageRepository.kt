package com.trubitsyna.homework.data.repository

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun saveImage(uri: Uri): Uri?
    suspend fun deleteImage(uri: Uri)
}