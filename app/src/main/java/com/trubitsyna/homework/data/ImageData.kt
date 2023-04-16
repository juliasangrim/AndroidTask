package com.trubitsyna.homework.data

import java.util.UUID

data class ImageData(
    val id: String = UUID.randomUUID().toString(),
    val imageUrl: String
)