package com.trubitsyna.homework.domain.post

import com.trubitsyna.homework.data.repository.PostRepository
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val postRepositoryImpl: PostRepository,
) {
    suspend fun execute(postId: String): Boolean {
        return postRepositoryImpl.deletePost(postId)
    }
}