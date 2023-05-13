package com.trubitsyna.homework.domain

import androidx.paging.PagingData
import com.trubitsyna.homework.data.local.model.Post
import com.trubitsyna.homework.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFeedUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    fun execute(): Flow<PagingData<Post>> {
        return postRepository.getFeed()
    }
}