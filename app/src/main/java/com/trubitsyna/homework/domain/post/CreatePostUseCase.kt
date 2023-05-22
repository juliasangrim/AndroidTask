package com.trubitsyna.homework.domain.post

import com.trubitsyna.homework.data.local.model.Post
import com.trubitsyna.homework.data.repository.PostRepository
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend fun execute(text: String?, list: List<ByteArray>?): Post {
        return postRepository.createPost(text, list)
    }
}