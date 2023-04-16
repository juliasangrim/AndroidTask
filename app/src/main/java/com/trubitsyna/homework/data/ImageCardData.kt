package com.trubitsyna.homework.data

import java.util.UUID

data class ImageCardData(
    val id: String = UUID.randomUUID().toString(),
    val listImagesUrl: List<String>
)