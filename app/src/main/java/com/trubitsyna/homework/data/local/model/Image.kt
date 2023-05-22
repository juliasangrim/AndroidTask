package com.trubitsyna.homework.data.local.model

data class Image(
    val id: String,
    val owner: ProfileCompact,
    val dateCreated: Long,
    val sizes: List<ImageSize>
)