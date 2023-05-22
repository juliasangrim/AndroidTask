package com.trubitsyna.homework.utils

import com.trubitsyna.homework.data.local.model.Image

fun Image.getMinSizeImageUrl(): String? {
    return this.sizes.minByOrNull {
        it.height * it.width
    }?.url
}

fun Image.getMaxSizeImageUrl(): String? {
    return this.sizes.maxByOrNull {
        it.height * it.width
    }?.url
}