package com.trubitsyna.homework.domain

import android.net.Uri
import com.trubitsyna.homework.data.repository.ImageRepository
import javax.inject.Inject

class DeleteImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
) {
    suspend fun execute(uri: Uri) {
        imageRepository.deleteImage(uri)
    }
}