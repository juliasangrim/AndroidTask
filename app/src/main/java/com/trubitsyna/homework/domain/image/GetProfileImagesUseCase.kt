package com.trubitsyna.homework.domain.image

import androidx.paging.PagingData
import com.trubitsyna.homework.data.local.model.Image
import com.trubitsyna.homework.data.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileImagesUseCase @Inject constructor(
    private val imageRepositoryImpl: ImageRepository
) {

    fun execute(profileId: String): Flow<PagingData<Image>> {
        return imageRepositoryImpl.getProfileImages(profileId)
    }
}