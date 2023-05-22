package com.trubitsyna.homework.data.mapper

import com.trubitsyna.homework.data.local.model.Post
import com.trubitsyna.homework.data.remote.model.ApiPost
import javax.inject.Inject

class PostMapper @Inject constructor(
    private val profileCompactMapper: ProfileCompactMapper,
    private val imageMapper: ImageMapper,
) {
    fun apiPostToPost(apiPost: ApiPost): Post {
        return Post(
            id = apiPost.id,
            owner = profileCompactMapper.apiProfileCompactToProfileCompact(apiPost.owner),
            dateCreated = apiPost.dateCreated,
            text = apiPost.text,
            images = apiPost.images.map {
                imageMapper.apiImageToImage(it)
            },
            likeCount = apiPost.likes.likesCount,
            isLiked = apiPost.likes.liked,
        )
    }

}