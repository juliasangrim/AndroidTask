package com.trubitsyna.homework.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiPost(
    val id: String,
    val owner: ApiProfileCompact,
    val dateCreated: Long,
    val text: String? = null,
    val images: List<ApiImage>,
    val likes: ApiLike
)