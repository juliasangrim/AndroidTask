package com.trubitsyna.homework.domain

import android.net.Uri
import com.trubitsyna.homework.data.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
) {
    suspend fun execute(uri: Uri): Uri? {
        return imageRepository.saveImage(uri)
    }
}