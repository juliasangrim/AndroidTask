package com.trubitsyna.homework.domain.image

import com.trubitsyna.homework.data.repository.ImageRepository
import javax.inject.Inject

class DeleteImageUseCase @Inject constructor(
    private val imageRepositoryImpl: ImageRepository,
) {
    suspend fun execute(imageId: String): Boolean {
        return imageRepositoryImpl.deleteImage(imageId)
    }
}