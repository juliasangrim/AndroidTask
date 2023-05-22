package com.trubitsyna.homework.data.local.model

data class Post(
    val id: String,
    val owner: ProfileCompact,
    val dateCreated: Long,
    val images: List<Image>,
    val text: String?,
    val likeCount: Int,
    val isLiked: Boolean,
)