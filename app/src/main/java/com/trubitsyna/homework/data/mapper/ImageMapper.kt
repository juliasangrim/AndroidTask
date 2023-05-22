package com.trubitsyna.homework.data.mapper

import com.trubitsyna.homework.data.local.model.Image
import com.trubitsyna.homework.data.remote.model.ApiImage
import javax.inject.Inject

class ImageMapper @Inject constructor(
    private val profileCompactMapper: ProfileCompactMapper,
    private val imageSizeMapper: ImageSizeMapper,
) {

    fun apiImageToImage(apiImage: ApiImage): Image {
        return Image(
            id = apiImage.id,
            owner = profileCompactMapper.apiProfileCompactToProfileCompact(apiImage.owner),
            dateCreated = apiImage.dateCreated,
            sizes = apiImage.sizes.map {
                imageSizeMapper.apiImageSizeToImageSize(it)
            }
        )
    }
}