package com.trubitsyna.homework.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.trubitsyna.homework.data.local.model.Image
import com.trubitsyna.homework.data.mapper.ImageMapper
import com.trubitsyna.homework.data.paging.ImagePagingSource
import com.trubitsyna.homework.data.remote.NanoPostApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val nanoPostApiService: NanoPostApiService,
    private val mapper: ImageMapper,
) : ImageRepository {
    override fun getProfileImages(
        profileId: String,
    ): Flow<PagingData<Image>> {
        return Pager(
            config = PagingConfig(30, enablePlaceholders = false),
            pagingSourceFactory = {
                ImagePagingSource(
                    profileId,
                    nanoPostApiService,
                )
            }
        ).flow.map { value ->
            value.map {
                mapper.apiImageToImage(it)
            }
        }
    }

    override suspend fun getImage(imageId: String): Image {
        return mapper.apiImageToImage(nanoPostApiService.getImage(imageId))
    }

    override suspend fun deleteImage(imageId: String): Boolean {
        return nanoPostApiService.deleteImage(imageId).result
    }
}