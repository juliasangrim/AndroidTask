package com.trubitsyna.homework.domain.image

import com.trubitsyna.homework.data.local.model.Image
import com.trubitsyna.homework.data.repository.ImageRepository
import javax.inject.Inject

class GetImageUseCase @Inject constructor(
    private val imageRepositoryImpl: ImageRepository
) {
    suspend fun execute(imageId: String): Image {
        return imageRepositoryImpl.getImage(imageId)
    }
}