package com.trubitsyna.homework.domain.post

import com.trubitsyna.homework.data.local.model.Post
import com.trubitsyna.homework.data.repository.PostRepository
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val postRepositoryImpl: PostRepository,
) {
    suspend fun execute(postId: String): Post {
        return postRepositoryImpl.getPost(postId)
    }
}