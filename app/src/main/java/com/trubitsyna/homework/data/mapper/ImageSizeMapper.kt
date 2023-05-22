package com.trubitsyna.homework.data.mapper

import com.trubitsyna.homework.data.local.model.ImageSize
import com.trubitsyna.homework.data.remote.model.ApiImageSize
import javax.inject.Inject

class ImageSizeMapper @Inject constructor() {

    fun apiImageSizeToImageSize(apiImageSize: ApiImageSize): ImageSize {
        return ImageSize(
            width = apiImageSize.width,
            height = apiImageSize.height,
            url = apiImageSize.url,
        )
    }
}